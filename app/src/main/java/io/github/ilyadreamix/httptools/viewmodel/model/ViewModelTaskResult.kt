package io.github.ilyadreamix.httptools.viewmodel.model

import io.github.ilyadreamix.httptools.viewmodel.enumeration.ViewModelTaskState

data class ViewModelTaskResult<T>(
    val state: ViewModelTaskState = ViewModelTaskState.IDLE,
    val data: T? = null
)
