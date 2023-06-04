package io.github.ilyadreamix.httptools.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.ilyadreamix.httptools.request.RequestFile
import io.github.ilyadreamix.httptools.request.model.Request
import io.github.ilyadreamix.httptools.utility.viewModelState
import io.github.ilyadreamix.httptools.viewmodel.enumeration.ViewModelTaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class HomeViewModel(filesDir: File) : ViewModel() {
    private val _requestListResult = MutableStateFlow(ViewModelTaskResult())
    val requestListResult = _requestListResult.asStateFlow()

    private val requestFile = RequestFile(filesDir)

    init {
        readRequestList()
    }

    private fun readRequestList() {
        viewModelScope.launch {
            _requestListResult.update {
                it.copy(state = ViewModelTaskState.LOADING)
            }

            val result = requestFile.readRequestList()

            _requestListResult.update {
                ViewModelTaskResult(
                    state = result.viewModelState,
                    data = result
                )
            }
        }
    }

    fun saveRequestList(requestList: List<Request>) {
        viewModelScope.launch {
            _requestListResult.update {
                it.copy(state = ViewModelTaskState.LOADING)
            }

            requestFile.saveRequestList(requestList)

            _requestListResult.update {
                ViewModelTaskResult(
                    state = ViewModelTaskState.SUCCESS,
                    data = requestList
                )
            }
        }
    }

    fun deleteRequestList() {
        _requestListResult.update {
            it.copy(state = ViewModelTaskState.LOADING)
        }

        requestFile.deleteRequestList()

        _requestListResult.update {
            ViewModelTaskResult(state = ViewModelTaskState.ERROR)
        }
    }

    fun updateRequestList(requestList: List<Request>) {
        _requestListResult.update {
            it.copy(data = requestList)
        }

        viewModelScope.launch {
            requestFile.saveRequestList(requestList)
        }
    }
}
