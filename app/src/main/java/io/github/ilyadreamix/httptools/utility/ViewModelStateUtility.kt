package io.github.ilyadreamix.httptools.utility

import io.github.ilyadreamix.httptools.viewmodel.enumeration.ViewModelTaskState

val Any?.viewModelState get() = when {
    this == null -> ViewModelTaskState.ERROR
    this is Collection<*> && this.isEmpty() -> ViewModelTaskState.ERROR
    else -> ViewModelTaskState.SUCCESS
}
