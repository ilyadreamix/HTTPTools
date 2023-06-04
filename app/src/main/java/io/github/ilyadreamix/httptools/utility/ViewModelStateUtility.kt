package io.github.ilyadreamix.httptools.utility

import io.github.ilyadreamix.httptools.viewmodel.enumeration.HTViewModelTaskState

val Any?.viewModelState get() = when {
    this == null -> HTViewModelTaskState.ERROR
    this is Collection<*> && this.isEmpty() -> HTViewModelTaskState.ERROR
    else -> HTViewModelTaskState.SUCCESS
}
