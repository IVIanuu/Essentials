/*
 * Copyright 2019 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.essentials.kotlin.compiler.compose

import com.ivianuu.essentials.kotlin.compiler.compose.ast.Converter
import com.ivianuu.essentials.kotlin.compiler.compose.ast.Node
import com.ivianuu.essentials.kotlin.compiler.compose.ast.Visitor
import com.ivianuu.essentials.kotlin.compiler.compose.ast.Writer
import com.squareup.kotlinpoet.CodeBlock
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.startOffset
import org.jetbrains.kotlin.resolve.BindingTrace
import org.jetbrains.kotlin.resolve.calls.callUtil.getResolvedCall
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.resolve.lazy.ResolveSession
import org.jetbrains.kotlin.types.typeUtil.isUnit

fun test(
    trace: BindingTrace,
    resolveSession: ResolveSession,
    file: KtFile
): KtFile? {
    val fileNode = Converter().convertFile(file)

    /*val allNodes = mutableListOf<Node?>()
    Visitor.visit(fileNode) { node, _ ->
        allNodes += node
    }

    error("all nodes $allNodes")'*/

    insertRestartScope(fileNode, resolveSession, trace)

    wrapComposableCalls(fileNode, resolveSession, trace)

    val newSource = Writer.write(fileNode)

    //error("new source $newSource")

    return file.withNewSource(newSource)
}

private fun insertRestartScope(
    file: Node.File,
    resolveSession: ResolveSession,
    trace: BindingTrace
) {
    Visitor.visit(file) { node, parent ->
        if (node !is Node.Decl.Func) return@visit
        val body = node.body as? Node.Decl.Func.Body.Block ?: return@visit
        val block = body.block
        val element = node.element as? KtNamedFunction ?: return@visit
        val descriptor =
            resolveSession.resolveToDescriptor(element) as FunctionDescriptor
        if (!descriptor.annotations.hasAnnotation(ComposableAnnotation)) return@visit
        if (descriptor.returnType?.isUnit() != true) return@visit

        var composableCalls = 0
        Visitor.visit(node) { childNode, _ ->
            if (childNode !is Node.Expr.Call) return@visit
            val childElement = childNode.element as? KtCallExpression ?: return@visit
            val resolvedCall = childElement.getResolvedCall(trace.bindingContext)!!
            val resulting = resolvedCall.resultingDescriptor
            if (!resulting.annotations.hasAnnotation(ComposableAnnotation) || resulting.returnType?.isUnit() == false) {
                return@visit
            }

            ++composableCalls
        }

        if (composableCalls == 0) return@visit

        val funcKey = "${descriptor.fqNameSafe.asString()}:${element.startOffset}".hashCode()
        val newStmts = block.stmts.toMutableList()

        val getComposerStmt = Node.Stmt.Expr(
            Node.Expr.Text("val compose_composer = androidx.compose.composer")
        )
        newStmts.add(0, getComposerStmt)

        val startRestartGroupStmt = Node.Stmt.Expr(
            Node.Expr.Text("compose_composer.startRestartGroup($funcKey)")
        )
        newStmts.add(1, startRestartGroupStmt)

        val endRestartGroupStmt = Node.Stmt.Expr(
            Node.Expr.Text(
                CodeBlock.builder()
                    .beginControlFlow("compose_composer.endRestartGroup()?.updateScope {")
                    .addStatement(
                        "${descriptor.name}(${descriptor.valueParameters.map { it.name }.joinToString(
                            ", "
                        )})"
                    )
                    .endControlFlow()
                    .build()
                    .toString()
            )
        )
        newStmts += endRestartGroupStmt

        block.stmts = newStmts
    }
}

private fun wrapComposableCalls(
    file: Node.File,
    resolveSession: ResolveSession,
    trace: BindingTrace
) {
    var func: Node.Decl.Func? = null
    var keyIndex = 0
    fun nextKeyIndex() = ++keyIndex
    Visitor.visit(file) { node, parent ->
        if (node is Node.Decl.Func) {
            func = node
            keyIndex = 0
        }
        if (node !is Node.Expr.Call) return@visit
        if (func == null) return@visit
        val element = node.element as? KtCallExpression ?: return@visit
        val resolvedCall = element.getResolvedCall(trace.bindingContext)!!
        val resulting = resolvedCall.resultingDescriptor
        if (!resulting.annotations.hasAnnotation(ComposableAnnotation)) {
            return@visit
        }
        val isEffect = resulting.returnType?.isUnit() == false

        val funcDescriptor = resolveSession.resolveToDescriptor(func!!.element!! as KtNamedFunction)

        val callKey = "${funcDescriptor.fqNameSafe.asString()}:${element.startOffset}".hashCode()
        val callKeyIndex = nextKeyIndex()

        node.expr = Node.Expr.Text(
            CodeBlock.builder().apply {
                node.args.forEachIndexed { index, valueArg ->
                    addStatement("val arg_${callKeyIndex}_${index} = ${valueArg.element!!.text}")
                }

                beginControlFlow("with(Unit)")

                addStatement("var key_$callKeyIndex: Any = $callKey")

                resulting.valueParameters.forEachIndexed { index, param ->
                    if (param.annotations.hasAnnotation(PivotalAnnotation)) {
                        addStatement("key_$callKeyIndex = compose_composer.joinKey(key_$callKeyIndex, arg_${callKeyIndex}_${index})")
                    }
                }

                if (isEffect) {
                    addStatement("compose_composer.expr(")
                    indent()
                    addStatement("key = key_$callKeyIndex,")
                    addStatement(
                        "block = { ${node.expr.element!!.text}(${node.args.indices.joinToString(
                            ", "
                        ) { "arg_${callKeyIndex}_${it}" }}) }"
                    )
                    unindent()
                    addStatement(")")
                } else {
                    addStatement("compose_composer.call(")
                    indent()
                    addStatement("key = key_$callKeyIndex,")
                    addStatement("invalid = {")
                    indent()

                    if (resulting.valueParameters.isEmpty()) {
                        addStatement("false")
                    } else {
                        val stableParams = resulting.valueParameters
                            .filter { it.type.isStable() }

                        if (stableParams.size != resulting.valueParameters.size) {
                            addStatement("true")
                        } else {
                            addStatement(
                                stableParams.joinToString(" or ") {
                                    "changed(arg_${callKeyIndex}_${it.index})"
                                }
                            )
                        }
                    }
                    unindent()
                    addStatement("},")
                    addStatement(
                        "block = { ${node.expr.element!!.text}(${node.args.indices.joinToString(
                            ", "
                        ) { "arg_${callKeyIndex}_${it}" }}) }"
                    )
                    unindent()
                    addStatement(")")
                }

                endControlFlow()
            }.build().toString()
        )

        node.ugly = true
        node.args = emptyList()
        node.typeArgs = emptyList()
        node.lambda = null
    }
}