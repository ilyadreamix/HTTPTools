package io.github.ilyadreamix.httptools.request.ui

import androidx.lifecycle.ViewModel
import io.github.ilyadreamix.httptools.request.feature.model.Request
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RequestViewModel : ViewModel() {
    private val _request = MutableStateFlow<Request?>(null)
    val request = _request.asStateFlow()

    fun updateRequest(request: Request? = null) {
        _request.update { request }
    }
}
