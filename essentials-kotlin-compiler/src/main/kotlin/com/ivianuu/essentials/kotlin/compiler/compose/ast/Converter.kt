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

package com.ivianuu.essentials.kotlin.compiler.compose.ast

import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.PsiComment
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtAnnotatedExpression
import org.jetbrains.kotlin.psi.KtAnnotation
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget
import org.jetbrains.kotlin.psi.KtAnonymousInitializer
import org.jetbrains.kotlin.psi.KtArrayAccessExpression
import org.jetbrains.kotlin.psi.KtBinaryExpression
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtBlockStringTemplateEntry
import org.jetbrains.kotlin.psi.KtBreakExpression
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression
import org.jetbrains.kotlin.psi.KtCatchClause
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtClassLiteralExpression
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression
import org.jetbrains.kotlin.psi.KtConstantExpression
import org.jetbrains.kotlin.psi.KtContinueExpression
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry
import org.jetbrains.kotlin.psi.KtDoWhileExpression
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtDoubleColonExpression
import org.jetbrains.kotlin.psi.KtDynamicType
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtEnumEntry
import org.jetbrains.kotlin.psi.KtEscapeStringTemplateEntry
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFileAnnotationList
import org.jetbrains.kotlin.psi.KtForExpression
import org.jetbrains.kotlin.psi.KtFunctionLiteral
import org.jetbrains.kotlin.psi.KtFunctionType
import org.jetbrains.kotlin.psi.KtIfExpression
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.KtIsExpression
import org.jetbrains.kotlin.psi.KtLabeledExpression
import org.jetbrains.kotlin.psi.KtLambdaArgument
import org.jetbrains.kotlin.psi.KtLambdaExpression
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry
import org.jetbrains.kotlin.psi.KtModifierList
import org.jetbrains.kotlin.psi.KtModifierListOwner
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtNullableType
import org.jetbrains.kotlin.psi.KtObjectDeclaration
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression
import org.jetbrains.kotlin.psi.KtPackageDirective
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtParenthesizedExpression
import org.jetbrains.kotlin.psi.KtPrefixExpression
import org.jetbrains.kotlin.psi.KtPrimaryConstructor
import org.jetbrains.kotlin.psi.KtProjectionKind
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtPropertyAccessor
import org.jetbrains.kotlin.psi.KtQualifiedExpression
import org.jetbrains.kotlin.psi.KtReturnExpression
import org.jetbrains.kotlin.psi.KtSecondaryConstructor
import org.jetbrains.kotlin.psi.KtSimpleNameExpression
import org.jetbrains.kotlin.psi.KtSimpleNameStringTemplateEntry
import org.jetbrains.kotlin.psi.KtStringTemplateEntry
import org.jetbrains.kotlin.psi.KtStringTemplateExpression
import org.jetbrains.kotlin.psi.KtSuperExpression
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry
import org.jetbrains.kotlin.psi.KtThisExpression
import org.jetbrains.kotlin.psi.KtThrowExpression
import org.jetbrains.kotlin.psi.KtTryExpression
import org.jetbrains.kotlin.psi.KtTypeAlias
import org.jetbrains.kotlin.psi.KtTypeArgumentList
import org.jetbrains.kotlin.psi.KtTypeConstraint
import org.jetbrains.kotlin.psi.KtTypeElement
import org.jetbrains.kotlin.psi.KtTypeParameter
import org.jetbrains.kotlin.psi.KtTypeProjection
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUnaryExpression
import org.jetbrains.kotlin.psi.KtUserType
import org.jetbrains.kotlin.psi.KtValueArgument
import org.jetbrains.kotlin.psi.KtValueArgumentList
import org.jetbrains.kotlin.psi.KtWhenCondition
import org.jetbrains.kotlin.psi.KtWhenConditionInRange
import org.jetbrains.kotlin.psi.KtWhenConditionIsPattern
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression
import org.jetbrains.kotlin.psi.KtWhenEntry
import org.jetbrains.kotlin.psi.KtWhenExpression
import org.jetbrains.kotlin.psi.KtWhileExpressionBase
import org.jetbrains.kotlin.psi.psiUtil.children

class Converter {
    private fun onNode(node: Node, elem: PsiElement) {
        node.element = elem
    }

    fun convertAnnotated(v: KtAnnotatedExpression) = Node.Expr.Annotated(
        anns = convertAnnotationSets(v),
        expr = convertExpr(v.baseExpression ?: error("No annotated expr for $v"))
    ).map(v).let {
        // As a special case, instead of annotating a type/binary op, we mean to just annotate its lhs
        val expr = it.expr
        when (expr) {
            is Node.Expr.BinaryOp -> expr.copy(
                lhs = it.copy(expr = expr.lhs)
            )
            is Node.Expr.TypeOp -> expr.copy(
                lhs = it.copy(expr = expr.lhs)
            )
            else -> it
        }
    }

    fun convertAnnotation(v: KtAnnotationEntry) = Node.Modifier.AnnotationSet.Annotation(
        names = v.typeReference?.names ?: error("Missing annotation name"),
        typeArgs = v.typeArguments.map { convertType(it) ?: error("No ann typ arg for $v") },
        args = convertValueArgs(v.valueArgumentList)
    ).map(v)

    fun convertAnnotationSet(v: KtAnnotation) = Node.Modifier.AnnotationSet(
        target = v.useSiteTarget?.let(::convertAnnotationSetTarget),
        anns = v.entries.map(::convertAnnotation)
    ).map(v)

    fun convertAnnotationSets(v: KtElement): List<Node.Modifier.AnnotationSet> =
        v.children.flatMap { elem ->
            // We go over the node children because we want to preserve order
            when (elem) {
                is KtAnnotationEntry ->
                    listOf(
                        Node.Modifier.AnnotationSet(
                            target = elem.useSiteTarget?.let(::convertAnnotationSetTarget),
                            anns = listOf(convertAnnotation(elem))
                        ).map(elem)
                    )
                is KtAnnotation ->
                    listOf(convertAnnotationSet(elem))
                is KtFileAnnotationList ->
                    convertAnnotationSets(elem)
                else ->
                    emptyList()
            }
        }

    fun convertCommands(v: KtElement): List<Node.Command> =
        v.children.filterIsInstance<PsiComment>().filter { it.text.startsWith(COMMAND_PREFIX) }.map {
            Node.Command(
                name = it.text
            )
        }

    fun convertAnnotationSetTarget(v: KtAnnotationUseSiteTarget) =
        when (v.getAnnotationUseSiteTarget()) {
            AnnotationUseSiteTarget.FIELD -> Node.Modifier.AnnotationSet.Target.FIELD
            AnnotationUseSiteTarget.FILE -> Node.Modifier.AnnotationSet.Target.FILE
            AnnotationUseSiteTarget.PROPERTY -> Node.Modifier.AnnotationSet.Target.PROPERTY
            AnnotationUseSiteTarget.PROPERTY_GETTER -> Node.Modifier.AnnotationSet.Target.GET
            AnnotationUseSiteTarget.PROPERTY_SETTER -> Node.Modifier.AnnotationSet.Target.SET
            AnnotationUseSiteTarget.RECEIVER -> Node.Modifier.AnnotationSet.Target.RECEIVER
            AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER -> Node.Modifier.AnnotationSet.Target.PARAM
            AnnotationUseSiteTarget.SETTER_PARAMETER -> Node.Modifier.AnnotationSet.Target.SETPARAM
            AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD -> Node.Modifier.AnnotationSet.Target.DELEGATE
        }

    fun convertAnonFunc(v: KtNamedFunction) = Node.Expr.AnonFunc(convertFunc(v))

    fun convertArrayAccess(v: KtArrayAccessExpression) = Node.Expr.ArrayAccess(
        expr = convertExpr(v.arrayExpression ?: error("No array expr for $v")),
        indices = v.indexExpressions.map(::convertExpr)
    ).map(v)

    fun convertBinaryOp(v: KtBinaryExpression) = Node.Expr.BinaryOp(
        lhs = convertExpr(v.left ?: error("No binary lhs for $v")),
        oper = binaryTokensByText[v.operationReference.text].let {
            if (it != null) Node.Expr.BinaryOp.Oper.Token(it).map(v.operationReference)
            else Node.Expr.BinaryOp.Oper.Infix(v.operationReference.text).map(v.operationReference)
        },
        rhs = convertExpr(v.right ?: error("No binary rhs for $v"))
    ).map(v)

    fun convertBinaryOp(v: KtQualifiedExpression) = Node.Expr.BinaryOp(
        lhs = convertExpr(v.receiverExpression),
        oper = Node.Expr.BinaryOp.Oper.Token(
            if (v is KtDotQualifiedExpression) Node.Expr.BinaryOp.Token.DOT else Node.Expr.BinaryOp.Token.DOT_SAFE
        ),
        rhs = convertExpr(v.selectorExpression ?: error("No qualified rhs for $v"))
    ).map(v)

    fun convertBlock(v: KtBlockExpression) = Node.Block(
        stmts = v.statements.map(::convertStmtNo)
    ).map(v)

    fun convertBrace(v: KtBlockExpression) = Node.Expr.Brace(
        params = emptyList(),
        block = convertBlock(v)
    ).map(v)

    fun convertBrace(v: KtFunctionLiteral) = Node.Expr.Brace(
        params = v.valueParameters.map(::convertBraceParam),
        block = v.bodyExpression?.let(::convertBlock)
    ).map(v)

    fun convertBrace(v: KtLambdaExpression) = Node.Expr.Brace(
        params = v.valueParameters.map(::convertBraceParam),
        block = v.bodyExpression?.let(::convertBlock)
    ).map(v)

    fun convertBraceParam(v: KtParameter) = Node.Expr.Brace.Param(
        vars = convertPropertyVars(v),
        destructType = if (v.destructuringDeclaration != null) v.typeReference?.let(::convertType) else null
    ).map(v)

    fun convertBreak(v: KtBreakExpression) = Node.Expr.Break(
        label = v.getLabelName()
    ).map(v)

    fun convertCall(v: KtCallExpression) = Node.Expr.Call(
        expr = convertExpr(v.calleeExpression ?: error("No call expr for $v")),
        typeArgs = v.typeArguments.map(::convertType),
        args = convertValueArgs(v.valueArgumentList),
        lambda = v.lambdaArguments.singleOrNull()?.let(::convertCallTrailLambda)
    ).map(v)

    fun convertCallTrailLambda(v: KtLambdaArgument): Node.Expr.Call.TrailLambda {
        var label: String? = null
        var anns: List<Node.Modifier.AnnotationSet> = emptyList()
        fun KtExpression.extractLambda(allowParens: Boolean = false): KtLambdaExpression? =
            when (this) {
                is KtLambdaExpression -> this
                is KtLabeledExpression -> baseExpression?.extractLambda(allowParens).also {
                    label = getLabelName()
                }
                is KtAnnotatedExpression -> baseExpression?.extractLambda(allowParens).also {
                    anns = convertAnnotationSets(this)
                }
                is KtParenthesizedExpression -> if (allowParens) expression?.extractLambda(
                    allowParens
                ) else null
                else -> null
            }

        val expr = v.getArgumentExpression()?.extractLambda() ?: error("No lambda for $v")
        return Node.Expr.Call.TrailLambda(
            anns = anns,
            label = label,
            func = convertBrace(expr)
        ).map(v)
    }

    fun convertCollLit(v: KtCollectionLiteralExpression) = Node.Expr.CollLit(
        exprs = v.getInnerExpressions().map(::convertExpr)
    ).map(v)

    fun convertConst(v: KtConstantExpression) = Node.Expr.Const(
        value = v.text,
        form = when (v.node.elementType) {
            KtNodeTypes.BOOLEAN_CONSTANT -> Node.Expr.Const.Form.BOOLEAN
            KtNodeTypes.CHARACTER_CONSTANT -> Node.Expr.Const.Form.CHAR
            KtNodeTypes.INTEGER_CONSTANT -> Node.Expr.Const.Form.INT
            KtNodeTypes.FLOAT_CONSTANT -> Node.Expr.Const.Form.FLOAT
            KtNodeTypes.NULL -> Node.Expr.Const.Form.NULL
            else -> error("Unrecognized const type for $v")
        }
    )

    fun convertConstructor(v: KtSecondaryConstructor) = Node.Decl.Constructor(
        mods = convertModifiers(v),
        params = v.valueParameters.map(::convertFuncParam),
        delegationCall = if (v.hasImplicitDelegationCall()) null else v.getDelegationCall().let {
            Node.Decl.Constructor.DelegationCall(
                target =
                if (it.isCallToThis) Node.Decl.Constructor.DelegationTarget.THIS
                else Node.Decl.Constructor.DelegationTarget.SUPER,
                args = convertValueArgs(it.valueArgumentList)
            ).map(it)
        },
        block = v.bodyExpression?.let(::convertBlock)
    ).map(v)

    fun convertContinue(v: KtContinueExpression) = Node.Expr.Continue(
        label = v.getLabelName()
    ).map(v)

    fun convertDecl(v: KtDeclaration): Node.Decl = when (v) {
        is KtEnumEntry -> convertEnumEntry(v)
        is KtClassOrObject -> convertStructured(v)
        is KtAnonymousInitializer -> convertInit(v)
        is KtNamedFunction -> convertFunc(v)
        is KtDestructuringDeclaration -> convertProperty(v)
        is KtProperty -> convertProperty(v)
        is KtTypeAlias -> convertTypeAlias(v)
        is KtSecondaryConstructor -> convertConstructor(v)
        else -> error("Unrecognized declaration type for $v")
    }

    fun convertDoubleColonRefCallable(v: KtCallableReferenceExpression) =
        Node.Expr.DoubleColonRef.Callable(
            recv = v.receiverExpression?.let { convertDoubleColonRefRecv(it, v.questionMarks) },
            name = v.callableReference.getReferencedName()
        ).map(v)

    fun convertDoubleColonRefClass(v: KtClassLiteralExpression) =
        Node.Expr.DoubleColonRef.Class(
            recv = v.receiverExpression?.let { convertDoubleColonRefRecv(it, v.questionMarks) }
        ).map(v)

    fun convertDoubleColonRefRecv(
        v: KtExpression,
        questionMarks: Int
    ): Node.Expr.DoubleColonRef.Recv = when (v) {
        is KtSimpleNameExpression -> Node.Expr.DoubleColonRef.Recv.Type(
            type = Node.TypeRef.Simple(
                listOf(Node.TypeRef.Simple.Piece(v.getReferencedName(), emptyList()).map(v))
            ).map(v),
            questionMarks = questionMarks
        ).map(v)
        is KtCallExpression ->
            if (v.valueArgumentList == null && v.lambdaArguments.isEmpty())
                Node.Expr.DoubleColonRef.Recv.Type(
                    type = Node.TypeRef.Simple(
                        listOf(
                            Node.TypeRef.Simple.Piece(
                                name = v.calleeExpression?.text
                                    ?: error("Missing text for call ref type of $v"),
                                typeParams = convertTypeParams(v.typeArgumentList)
                            ).map(v)
                        )
                    ).map(v),
                    questionMarks = questionMarks
                ).map(v)
            else Node.Expr.DoubleColonRef.Recv.Expr(convertExpr(v)).map(v)
        is KtDotQualifiedExpression -> {
            val lhs = convertDoubleColonRefRecv(v.receiverExpression, questionMarks)
            val rhs = v.selectorExpression?.let { convertDoubleColonRefRecv(it, questionMarks) }
            if (lhs is Node.Expr.DoubleColonRef.Recv.Type && rhs is Node.Expr.DoubleColonRef.Recv.Type)
                Node.Expr.DoubleColonRef.Recv.Type(
                    type = Node.TypeRef.Simple(lhs.type.pieces + rhs.type.pieces).map(v),
                    questionMarks = 0
                ).map(v)
            else Node.Expr.DoubleColonRef.Recv.Expr(convertExpr(v)).map(v)
        }
        else -> Node.Expr.DoubleColonRef.Recv.Expr(convertExpr(v)).map(v)
    }

    fun convertEnumEntry(v: KtEnumEntry) = Node.Decl.EnumEntry(
        mods = convertModifiers(v),
        name = v.name ?: error("Unnamed enum"),
        args = convertValueArgs((v.superTypeListEntries.firstOrNull() as? KtSuperTypeCallEntry)?.valueArgumentList),
        members = v.declarations.map(::convertDecl)
    ).map(v)

    fun convertExpr(v: KtExpression): Node.Expr = when (v) {
        is KtIfExpression -> convertIf(v)
        is KtTryExpression -> convertTry(v)
        is KtForExpression -> convertFor(v)
        is KtWhileExpressionBase -> convertWhile(v)
        is KtBinaryExpression -> convertBinaryOp(v)
        is KtQualifiedExpression -> convertBinaryOp(v)
        is KtUnaryExpression -> convertUnaryOp(v)
        is KtBinaryExpressionWithTypeRHS -> convertTypeOp(v)
        is KtIsExpression -> convertTypeOp(v)
        is KtCallableReferenceExpression -> convertDoubleColonRefCallable(v)
        is KtClassLiteralExpression -> convertDoubleColonRefClass(v)
        is KtParenthesizedExpression -> convertParen(v)
        is KtStringTemplateExpression -> convertStringTmpl(v)
        is KtConstantExpression -> convertConst(v)
        is KtBlockExpression -> convertBrace(v)
        is KtFunctionLiteral -> convertBrace(v)
        is KtLambdaExpression -> convertBrace(v)
        is KtThisExpression -> convertThis(v)
        is KtSuperExpression -> convertSuper(v)
        is KtWhenExpression -> convertWhen(v)
        is KtObjectLiteralExpression -> convertObject(v)
        is KtThrowExpression -> convertThrow(v)
        is KtReturnExpression -> convertReturn(v)
        is KtContinueExpression -> convertContinue(v)
        is KtBreakExpression -> convertBreak(v)
        is KtCollectionLiteralExpression -> convertCollLit(v)
        is KtSimpleNameExpression -> convertName(v)
        is KtLabeledExpression -> convertLabeled(v)
        is KtAnnotatedExpression -> convertAnnotated(v)
        is KtCallExpression -> convertCall(v)
        is KtArrayAccessExpression -> convertArrayAccess(v)
        is KtNamedFunction -> convertAnonFunc(v)
        is KtProperty -> convertPropertyExpr(v)
        is KtDestructuringDeclaration -> convertPropertyExpr(v)
        // TODO: this is present in a recovery test where an interface decl is on rhs of a gt expr
        is KtClass -> error(
            "Class expressions not supported"
        )
        else -> error("Unrecognized expression type from $v")
    }

    fun convertFile(v: KtFile) = Node.File(
        anns = convertAnnotationSets(v),
        pkg = v.packageDirective?.takeIf { it.packageNames.isNotEmpty() }?.let(::convertPackage),
        imports = v.importDirectives.map(::convertImport),
        commands = convertCommands(v),
        decls = v.declarations.map(::convertDecl)
    ).map(v)

    fun convertFor(v: KtForExpression) = Node.Expr.For(
        anns = v.loopParameter?.annotations?.map(::convertAnnotationSet) ?: emptyList(),
        vars = convertPropertyVars(v.loopParameter ?: error("No param on for $v")),
        inExpr = convertExpr(v.loopRange ?: error("No in range for $v")),
        body = convertExpr(v.body ?: error("No body for $v"))
    ).map(v)

    fun convertFunc(v: KtNamedFunction) = Node.Decl.Func(
        mods = convertModifiers(v),
        typeParams =
        if (v.hasTypeParameterListBeforeFunctionName()) v.typeParameters.map(::convertTypeParam) else emptyList(),
        receiverType = v.receiverTypeReference?.let(::convertType),
        name = v.name,
        paramTypeParams =
        if (!v.hasTypeParameterListBeforeFunctionName()) v.typeParameters.map(::convertTypeParam) else emptyList(),
        params = v.valueParameters.map(::convertFuncParam),
        type = v.typeReference?.let(::convertType),
        typeConstraints = v.typeConstraints.map(::convertTypeConstraint),
        body = v.bodyExpression?.let(::convertFuncBody)
    ).map(v)

    fun convertFuncBody(v: KtExpression) =
        if (v is KtBlockExpression) Node.Decl.Func.Body.Block(convertBlock(v)).map(v)
        else Node.Decl.Func.Body.Expr(convertExpr(v)).map(v)

    fun convertFuncParam(v: KtParameter) = Node.Decl.Func.Param(
        mods = convertModifiers(v),
        readOnly = if (v.hasValOrVar()) !v.isMutable else null,
        name = v.name ?: error("No param name"),
        type = v.typeReference?.let(::convertType),
        default = v.defaultValue?.let(::convertExpr)
    ).map(v)

    fun convertIf(v: KtIfExpression) = Node.Expr.If(
        expr = convertExpr(v.condition ?: error("No cond on if for $v")),
        body = convertExpr(v.then ?: error("No then on if for $v")),
        elseBody = v.`else`?.let(::convertExpr)
    ).map(v)

    fun convertImport(v: KtImportDirective) = Node.Import(
        names = v.importedFqName?.pathSegments()?.map { it.asString() }
            ?: error("Missing import path"),
        wildcard = v.isAllUnder,
        alias = v.aliasName
    ).map(v)

    fun convertInit(v: KtAnonymousInitializer) = Node.Decl.Init(
        block = convertBlock(v.body as? KtBlockExpression ?: error("No init block for $v"))
    ).map(v)

    fun convertLabeled(v: KtLabeledExpression) = Node.Expr.Labeled(
        label = v.getLabelName() ?: error("No label name for $v"),
        expr = convertExpr(v.baseExpression ?: error("No label expr for $v"))
    ).map(v)

    fun convertModifiers(v: KtModifierListOwner) = convertModifiers(v.modifierList)

    fun convertModifiers(v: KtModifierList?) =
        v?.node?.children().orEmpty().mapNotNull { node ->
            // We go over the node children because we want to preserve order
            node.psi.let {
                when (it) {
                    is KtAnnotationEntry -> Node.Modifier.AnnotationSet(
                        target = it.useSiteTarget?.let(::convertAnnotationSetTarget),
                        anns = listOf(convertAnnotation(it))
                    ).map(it)
                    is KtAnnotation -> convertAnnotationSet(it)
                    is PsiWhiteSpace -> null
                    else -> when (node.text) {
                        // We ignore some modifiers because we handle them elsewhere
                        "enum", "companion" -> null
                        else -> modifiersByText[node.text]?.let {
                            Node.Modifier.Lit(it)
                                .let { lit -> (node.psi as? KtElement)?.let { lit.map(it) } ?: lit }
                        } ?: error("Unrecognized modifier: ${node.text}")
                    }
                }
            }
        }.toList()

    fun convertName(v: KtSimpleNameExpression) = Node.Expr.Name(
        name = v.getReferencedName()
    ).map(v)

    fun convertObject(v: KtObjectLiteralExpression) = Node.Expr.Object(
        parents = v.objectDeclaration.superTypeListEntries.map(::convertParent),
        members = v.objectDeclaration.declarations.map(::convertDecl)
    ).map(v)

    fun convertPackage(v: KtPackageDirective) = Node.Package(
        mods = convertModifiers(v),
        names = v.packageNames.map { it.getReferencedName() }
    ).map(v)

    fun convertParen(v: KtParenthesizedExpression) = Node.Expr.Paren(
        expr = convertExpr(v.expression ?: error("No paren expr for $v"))
    )

    fun convertParent(v: KtSuperTypeListEntry) = when (v) {
        is KtSuperTypeCallEntry -> Node.Decl.Structured.Parent.CallConstructor(
            type = v.typeReference?.let(::convertTypeRef) as? Node.TypeRef.Simple
                ?: error("Bad type on super call $v"),
            typeArgs = v.typeArguments.map(::convertType),
            args = convertValueArgs(v.valueArgumentList),
            // TODO
            lambda = null
        ).map(v)
        else -> Node.Decl.Structured.Parent.Type(
            type = v.typeReference?.let(::convertTypeRef) as? Node.TypeRef.Simple
                ?: error("Bad type on super call $v"),
            by = (v as? KtDelegatedSuperTypeEntry)?.delegateExpression?.let(::convertExpr)
        ).map(v)
    }

    fun convertPrimaryConstructor(v: KtPrimaryConstructor) =
        Node.Decl.Structured.PrimaryConstructor(
            mods = convertModifiers(v),
            params = v.valueParameters.map(::convertFuncParam)
        ).map(v)

    fun convertProperty(v: KtDestructuringDeclaration) = Node.Decl.Property(
        mods = convertModifiers(v),
        readOnly = !v.isVar,
        typeParams = emptyList(),
        receiverType = null,
        vars = v.entries.map(::convertPropertyVar),
        typeConstraints = emptyList(),
        delegated = false,
        expr = v.initializer?.let(::convertExpr),
        accessors = null
    ).map(v)

    fun convertProperty(v: KtProperty) = Node.Decl.Property(
        mods = convertModifiers(v),
        readOnly = !v.isVar,
        typeParams = v.typeParameters.map(::convertTypeParam),
        receiverType = v.receiverTypeReference?.let(::convertType),
        vars = listOf(
            Node.Decl.Property.Var(
                name = v.name ?: error("No property name on $v"),
                type = v.typeReference?.let(::convertType)
            ).map(v)
        ),
        typeConstraints = v.typeConstraints.map(::convertTypeConstraint),
        delegated = v.hasDelegateExpression(),
        expr = v.delegateExpressionOrInitializer?.let(::convertExpr),
        accessors = v.accessors.map(::convertPropertyAccessor).let {
            if (it.isEmpty()) null else Node.Decl.Property.Accessors(
                first = it.first(),
                second = it.getOrNull(1)
            )
        }
    ).map(v)

    fun convertPropertyAccessor(v: KtPropertyAccessor) =
        if (v.isGetter) Node.Decl.Property.Accessor.Get(
            mods = convertModifiers(v),
            type = v.returnTypeReference?.let(::convertType),
            body = v.bodyExpression?.let(::convertFuncBody)
        ).map(v) else Node.Decl.Property.Accessor.Set(
            mods = convertModifiers(v),
            paramMods = v.parameter?.let(::convertModifiers) ?: emptyList(),
            paramName = v.parameter?.name,
            paramType = v.parameter?.typeReference?.let(::convertType),
            body = v.bodyExpression?.let(::convertFuncBody)
        ).map(v)

    fun convertPropertyExpr(v: KtDestructuringDeclaration) = Node.Expr.Property(
        decl = convertProperty(v)
    ).map(v)

    fun convertPropertyExpr(v: KtProperty) = Node.Expr.Property(
        decl = convertProperty(v)
    ).map(v)

    fun convertPropertyVar(v: KtDestructuringDeclarationEntry) =
        if (v.name == "_") null else Node.Decl.Property.Var(
            name = v.name ?: error("No property name on $v"),
            type = v.typeReference?.let(::convertType)
        ).map(v)

    fun convertPropertyVars(v: KtParameter): List<Node.Decl.Property.Var?> {
        return v.destructuringDeclaration?.entries?.map(::convertPropertyVar) ?: listOf(
            Node.Decl.Property.Var(
                name = v.name ?: error("No property name on $v"),
                type = v.typeReference?.let(::convertType)
            ).map(v)
        )
    }

    fun convertReturn(v: KtReturnExpression) = Node.Expr.Return(
        label = v.getLabelName(),
        expr = v.returnedExpression?.let(::convertExpr)
    ).map(v)

    fun convertStmtNo(v: KtExpression) =
        if (v is KtDeclaration) Node.Stmt.Decl(convertDecl(v)).map(v) else Node.Stmt.Expr(
            convertExpr(v)
        ).map(v)

    fun convertStringTmpl(v: KtStringTemplateExpression) = Node.Expr.StringTmpl(
        elems = v.entries.map(::convertStringTmplElem),
        raw = v.text.startsWith("\"\"\"")
    ).map(v)

    fun convertStringTmplElem(v: KtStringTemplateEntry) = when (v) {
        is KtLiteralStringTemplateEntry ->
            Node.Expr.StringTmpl.Elem.Regular(v.text).map(v)
        is KtSimpleNameStringTemplateEntry -> {
            Node.Expr.StringTmpl.Elem.ShortTmpl(
                convertExpr(v.expression ?: error("No expr tmpl"))
            ).map(v)
        }
        is KtBlockStringTemplateEntry ->
            Node.Expr.StringTmpl.Elem.LongTmpl(
                convertExpr(
                    v.expression ?: error("No expr tmpl")
                )
            ).map(v)
        is KtEscapeStringTemplateEntry ->
            if (v.text.startsWith("\\u"))
                Node.Expr.StringTmpl.Elem.UnicodeEsc(v.text.substring(2)).map(v)
            else
                Node.Expr.StringTmpl.Elem.RegularEsc(v.unescapedValue.first()).map(v)
        else ->
            error("Unrecognized string template type for $v")
    }

    fun convertStructured(v: KtClassOrObject) = Node.Decl.Structured(
        mods = convertModifiers(v),
        form = when (v) {
            is KtClass -> when {
                v.isEnum() -> Node.Decl.Structured.Form.ENUM_CLASS
                v.isInterface() -> Node.Decl.Structured.Form.INTERFACE
                else -> Node.Decl.Structured.Form.CLASS
            }
            is KtObjectDeclaration ->
                if (v.isCompanion()) Node.Decl.Structured.Form.COMPANION_OBJECT
                else Node.Decl.Structured.Form.OBJECT
            else -> error("Unknown type of $v")
        },
        name = v.name ?: error("Missing name"),
        typeParams = v.typeParameters.map(::convertTypeParam),
        primaryConstructor = v.primaryConstructor?.let(::convertPrimaryConstructor),
        // TODO: this
        parentAnns = emptyList(),
        parents = v.superTypeListEntries.map(::convertParent),
        typeConstraints = v.typeConstraints.map(::convertTypeConstraint),
        members = v.declarations.map(::convertDecl)
    ).map(v)

    fun convertSuper(v: KtSuperExpression) = Node.Expr.Super(
        typeArg = v.superTypeQualifier?.let(::convertType),
        label = v.getLabelName()
    ).map(v)

    fun convertThis(v: KtThisExpression) = Node.Expr.This(
        label = v.getLabelName()
    ).map(v)

    fun convertThrow(v: KtThrowExpression) = Node.Expr.Throw(
        expr = convertExpr(v.thrownExpression ?: error("No throw expr for $v"))
    ).map(v)

    fun convertTry(v: KtTryExpression) = Node.Expr.Try(
        block = convertBlock(v.tryBlock),
        catches = v.catchClauses.map(::convertTryCatch),
        finallyBlock = v.finallyBlock?.finalExpression?.let(::convertBlock)
    ).map(v)

    fun convertTryCatch(v: KtCatchClause) = Node.Expr.Try.Catch(
        anns = v.catchParameter?.annotations?.map(::convertAnnotationSet) ?: emptyList(),
        varName = v.catchParameter?.name ?: error("No catch param name for $v"),
        varType = v.catchParameter?.typeReference?.let(::convertTypeRef) as? Node.TypeRef.Simple
            ?: error("Invalid catch param type for $v"),
        block = convertBlock(v.catchBody as? KtBlockExpression ?: error("No catch block for $v"))
    ).map(v)

    fun convertType(v: KtTypeProjection) =
        v.typeReference?.let { convertType(it, v.modifierList) }

    fun convertType(v: KtTypeReference, modifierList: KtModifierList?): Node.Type = Node.Type(
        mods = convertModifiers(modifierList),
        ref = convertTypeRef(v)
    ).map(v)

    fun convertType(v: KtTypeReference): Node.Type = Node.Type(
        // Paren modifiers are inside the ref...
        mods = if (v.hasParentheses()) emptyList() else convertModifiers(v),
        ref = convertTypeRef(v)
    ).map(v)

    fun convertTypeAlias(v: KtTypeAlias) = Node.Decl.TypeAlias(
        mods = convertModifiers(v),
        name = v.name ?: error("No type alias name for $v"),
        typeParams = v.typeParameters.map(::convertTypeParam),
        type = convertType(v.getTypeReference() ?: error("No type alias ref for $v"))
    ).map(v)

    fun convertTypeConstraint(v: KtTypeConstraint) = Node.TypeConstraint(
        anns = v.children.mapNotNull {
            when (it) {
                is KtAnnotationEntry ->
                    Node.Modifier.AnnotationSet(
                        target = null,
                        anns = listOf(convertAnnotation(it))
                    ).map(it)
                is KtAnnotation -> convertAnnotationSet(it)
                else -> null
            }
        },
        name = v.subjectTypeParameterName?.getReferencedName()
            ?: error("No type constraint name for $v"),
        type = convertType(v.boundTypeReference ?: error("No type constraint type for $v"))
    ).map(v)

    fun convertTypeOp(v: KtBinaryExpressionWithTypeRHS) = Node.Expr.TypeOp(
        lhs = convertExpr(v.left),
        oper = v.operationReference.let {
            Node.Expr.TypeOp.Oper(typeTokensByText[it.text] ?: error("Unable to find op ref $it"))
                .map(it)
        },
        rhs = convertType(v.right ?: error("No type op rhs for $v"))
    ).map(v)

    fun convertTypeOp(v: KtIsExpression) = Node.Expr.TypeOp(
        lhs = convertExpr(v.leftHandSide),
        oper = v.operationReference.let {
            Node.Expr.TypeOp.Oper(typeTokensByText[it.text] ?: error("Unable to find op ref $it"))
                .map(it)
        },
        rhs = convertType(v.typeReference ?: error("No type op rhs for $v"))
    )

    fun convertTypeParam(v: KtTypeParameter) = Node.TypeParam(
        mods = convertModifiers(v),
        name = v.name ?: error("No type param name for $v"),
        type = v.extendsBound?.let(::convertTypeRef)
    ).map(v)

    fun convertTypeParams(v: KtTypeArgumentList?) = v?.arguments?.map {
        if (it.projectionKind == KtProjectionKind.STAR) null
        else convertType(it)
    } ?: emptyList()

    fun convertTypeRef(v: KtTypeReference) =
        convertTypeRef(v.typeElement ?: error("Missing typ elem")).let {
            if (!v.hasParentheses()) it else Node.TypeRef.Paren(
                mods = convertModifiers(v),
                type = it
            ).map(v)
        }

    fun convertTypeRef(v: KtTypeElement): Node.TypeRef = when (v) {
        is KtFunctionType -> Node.TypeRef.Func(
            receiverType = v.receiverTypeReference?.let(::convertType),
            params = v.parameters.map {
                Node.TypeRef.Func.Param(
                    name = it.name,
                    type = convertType(it.typeReference ?: error("No param type"))
                ).map(it)
            },
            type = convertType(v.returnTypeReference ?: error("No return type"))
        ).map(v)
        is KtUserType -> Node.TypeRef.Simple(
            pieces = generateSequence(v) { it.qualifier }.toList().reversed().map {
                Node.TypeRef.Simple.Piece(
                    name = it.referencedName ?: error("No type name for $it"),
                    typeParams = convertTypeParams(it.typeArgumentList)
                ).map(it)
            }
        ).map(v)
        is KtNullableType -> Node.TypeRef.Nullable(
            // If there are modifiers or the inner type is a function, the type is a paren
            type = convertModifiers(v.modifierList).let { mods ->
                val innerType = convertTypeRef(v.innerType ?: error("No inner type for nullable"))
                if (v.innerType !is KtFunctionType && mods.isEmpty()) innerType
                else Node.TypeRef.Paren(mods, convertTypeRef(v.innerType!!))
            }
        ).map(v)
        is KtDynamicType -> Node.TypeRef.Dynamic().map(v)
        else -> error("Unrecognized type of $v")
    }

    fun convertUnaryOp(v: KtUnaryExpression) = Node.Expr.UnaryOp(
        expr = convertExpr(v.baseExpression ?: error("No unary expr for $v")),
        oper = v.operationReference.let {
            Node.Expr.UnaryOp.Oper(unaryTokensByText[it.text] ?: error("Unable to find op ref $it"))
                .map(it)
        },
        prefix = v is KtPrefixExpression
    ).map(v)

    fun convertValueArg(v: KtValueArgument) = Node.ValueArg(
        name = v.getArgumentName()?.asName?.asString(),
        asterisk = v.getSpreadElement() != null,
        expr = convertExpr(v.getArgumentExpression() ?: error("No expr for value arg"))
    ).map(v)

    fun convertValueArgs(v: KtValueArgumentList?) =
        v?.arguments?.map(::convertValueArg) ?: emptyList()

    fun convertWhen(v: KtWhenExpression) = Node.Expr.When(
        expr = v.subjectExpression?.let(::convertExpr),
        entries = v.entries.map(::convertWhenEntry)
    ).map(v)

    fun convertWhenCond(v: KtWhenCondition) = when (v) {
        is KtWhenConditionWithExpression -> Node.Expr.When.Cond.Expr(
            expr = convertExpr(v.expression ?: error("No when cond expr for $v"))
        ).map(v)
        is KtWhenConditionInRange -> Node.Expr.When.Cond.In(
            expr = convertExpr(v.rangeExpression ?: error("No when in expr for $v")),
            not = v.isNegated
        ).map(v)
        is KtWhenConditionIsPattern -> Node.Expr.When.Cond.Is(
            type = convertType(v.typeReference ?: error("No when is type for $v")),
            not = v.isNegated
        ).map(v)
        else -> error("Unrecognized when cond of $v")
    }

    fun convertWhenEntry(v: KtWhenEntry) = Node.Expr.When.Entry(
        conds = v.conditions.map(::convertWhenCond),
        body = convertExpr(v.expression ?: error("No when entry body for $v"))
    ).map(v)

    fun convertWhile(v: KtWhileExpressionBase) = Node.Expr.While(
        expr = convertExpr(v.condition ?: error("No while cond for $v")),
        body = convertExpr(v.body ?: error("No while body for $v")),
        doWhile = v is KtDoWhileExpression
    ).map(v)

    private fun <T : Node> T.map(v: PsiElement) = also { onNode(it, v) }

    companion object {
        private val modifiersByText =
            Node.Modifier.Keyword.values().map { it.name.toLowerCase() to it }.toMap()
        private val binaryTokensByText =
            Node.Expr.BinaryOp.Token.values().map { it.str to it }.toMap()
        private val unaryTokensByText =
            Node.Expr.UnaryOp.Token.values().map { it.str to it }.toMap()
        private val typeTokensByText = Node.Expr.TypeOp.Token.values().map { it.str to it }.toMap()

        private val KtTypeReference.names
            get() = (typeElement as? KtUserType)?.names ?: emptyList()
        private val KtUserType.names
            get(): List<String> =
                referencedName?.let { (qualifier?.names ?: emptyList()) + it } ?: emptyList()
        private val KtExpression?.block
            get() = (this as? KtBlockExpression)?.statements ?: emptyList()
        private val KtDoubleColonExpression.questionMarks
            get() =
                generateSequence(
                    node.firstChildNode,
                    ASTNode::getTreeNext
                ).takeWhile { it.elementType != KtTokens.COLONCOLON }.count { it.elementType == KtTokens.QUEST }
    }
}