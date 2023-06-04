package io.github.ilyadreamix.httptools.viewmodel.model

import io.github.ilyadreamix.httptools.viewmodel.enumeration.HTViewModelTaskState

data class HTViewModelTaskResult<T>(
    val state: HTViewModelTaskState = HTViewModelTaskState.IDLE,
    val data: T? = null
)
