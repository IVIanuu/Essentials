package com.ivianuu.essentials.ui.mvrx

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.ivianuu.androidktx.fragment.app.requireParentFragment
import com.ivianuu.androidktx.fragment.app.requireTargetFragment
import com.ivianuu.androidktx.lifecycle.defaultViewModelKey
import com.ivianuu.androidktx.lifecycle.viewModelProvider
import com.ivianuu.essentials.util.viewmodel.ViewModelFactoryHolder
import kotlin.reflect.KClass

inline fun <T : MvRxView, reified VM : MvRxViewModel<S>, reified S : MvRxState> T.viewModel(
    clazz: KClass<VM>,
    factory: ViewModelProvider.Factory = defaultViewModelFactory(),
    key: String = VM::class.defaultViewModelKey
) = viewModelProvider(factory).get(key, clazz.java).setupViewModel(this)

inline fun <T : MvRxView, reified VM : MvRxViewModel<S>, reified S : MvRxState> T.bindViewModel(
    clazz: KClass<VM>,
    crossinline keyProvider: () -> String = { VM::class.defaultViewModelKey },
    crossinline factoryProvider: () -> ViewModelProvider.Factory = { defaultViewModelFactory() }
) = viewModelLazy { viewModel(clazz, factoryProvider(), keyProvider()) }

inline fun <T : MvRxView, reified VM : MvRxViewModel<S>, reified S : MvRxState> T.activityViewModel(
    clazz: KClass<VM>,
    factory: ViewModelProvider.Factory = defaultViewModelFactory(),
    key: String = VM::class.defaultViewModelKey
): VM {
    val activity = when {
        this is FragmentActivity -> this
        this is Fragment -> requireActivity()
        else -> throw IllegalArgumentException("must be an activity or an fragment")
    }

    return activity.viewModelProvider(factory).get(key, clazz.java).setupViewModel(this)
}

inline fun <T : MvRxView, reified VM : MvRxViewModel<S>, reified S : MvRxState> T.bindActivityViewModel(
    clazz: KClass<VM>,
    crossinline keyProvider: () -> String = { VM::class.defaultViewModelKey },
    crossinline factoryProvider: () -> ViewModelProvider.Factory = { defaultViewModelFactory() }
) = viewModelLazy { activityViewModel(clazz, factoryProvider(), keyProvider()) }

inline fun <T, reified VM : MvRxViewModel<S>, reified S : MvRxState> T.parentViewModel(
    clazz: KClass<VM>,
    factory: ViewModelProvider.Factory = defaultViewModelFactory(),
    key: String = VM::class.defaultViewModelKey
) where T : MvRxView, T : Fragment =
    requireParentFragment().viewModelProvider(factory).get(key, clazz.java).setupViewModel(this)

inline fun <T, reified VM : MvRxViewModel<S>, reified S : MvRxState> T.bindParentViewModel(
    clazz: KClass<VM>,
    crossinline keyProvider: () -> String = { VM::class.defaultViewModelKey },
    crossinline factoryProvider: () -> ViewModelProvider.Factory = { defaultViewModelFactory() }
) where T : MvRxView, T : Fragment =
    viewModelLazy { parentViewModel(clazz, factoryProvider(), keyProvider()) }

inline fun <T, reified VM : MvRxViewModel<S>, reified S : MvRxState> T.targetViewModel(
    clazz: KClass<VM>,
    factory: ViewModelProvider.Factory = defaultViewModelFactory(),
    key: String = VM::class.defaultViewModelKey
) where T : MvRxView, T : Fragment =
    requireTargetFragment().viewModelProvider(factory).get(key, clazz.java).setupViewModel(this)

inline fun <T, reified VM : MvRxViewModel<S>, reified S : MvRxState> T.bindTargetViewModel(
    clazz: KClass<VM>,
    crossinline keyProvider: () -> String = { VM::class.defaultViewModelKey },
    crossinline factoryProvider: () -> ViewModelProvider.Factory = { defaultViewModelFactory() }
) where T : MvRxView, T : Fragment =
    viewModelLazy { targetViewModel(clazz, factoryProvider(), keyProvider()) }

@PublishedApi
internal fun <VM : MvRxViewModel<S>, S> VM.setupViewModel(view: MvRxView) =
    apply { subscribe(view) { view.postInvalidate() } }

@PublishedApi
internal fun Any.defaultViewModelFactory() = if (this is ViewModelFactoryHolder) {
    viewModelFactory
} else {
    ViewModelProvider.NewInstanceFactory()
}

@PublishedApi
internal fun <T : MvRxView, V> T.viewModelLazy(initializer: () -> V) =
    lifecycleAwareLazy(Lifecycle.Event.ON_CREATE, initializer)