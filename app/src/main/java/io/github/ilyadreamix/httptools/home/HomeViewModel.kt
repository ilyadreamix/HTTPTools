package io.github.ilyadreamix.httptools.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.ilyadreamix.httptools.request.feature.RequestFile
import io.github.ilyadreamix.httptools.request.feature.model.Request
import io.github.ilyadreamix.httptools.request.feature.model.RequestExtension
import io.github.ilyadreamix.httptools.utility.viewModelState
import io.github.ilyadreamix.httptools.viewmodel.enumeration.HTViewModelTaskState
import io.github.ilyadreamix.httptools.viewmodel.model.HTViewModelTaskResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class HomeViewModel(filesDir: File) : ViewModel() {
    private val _requestListResult = MutableStateFlow(ViewModelTaskResult())
    val requestListResult = _requestListResult.asStateFlow()

    private val requestFile = RequestFile(filesDir)

    init { readRequestList() }

    fun saveRequestList(requestList: List<Request>) {
        viewModelScope.launch {
            _requestListResult.update {
                it.copy(state = HTViewModelTaskState.LOADING)
            }

            requestFile.saveRequestList(requestList)

            _requestListResult.update {
                ViewModelTaskResult(
                    state = HTViewModelTaskState.SUCCESS,
                    data = requestList
                )
            }
        }
    }

    fun deleteRequestList() {
        _requestListResult.update {
            it.copy(state = HTViewModelTaskState.LOADING)
        }

        requestFile.deleteRequestList()

        _requestListResult.update {
            ViewModelTaskResult(state = HTViewModelTaskState.ERROR)
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

    fun updateFavouriteTime(updatedRequest: Request, favoriteTime: Long? = null) {
        viewModelScope.launch {
            val existingRequests = requestFile.readRequestList()?.toMutableList()
                ?: return@launch

            val requestToUpdate = existingRequests.find { it.id == updatedRequest.id }
                ?: return@launch

            if (favoriteTime == null) {
                requestToUpdate.extensions = requestToUpdate.extensions
                    .filterNot { extension -> extension.name == "favourite" }
            } else {
                requestToUpdate.extensions = requestToUpdate.extensions
                    .toMutableList()
                    .apply { add(RequestExtension("favourite", favoriteTime.toString())) }
            }

            existingRequests.removeIf { it.id == requestToUpdate.id }
            existingRequests.add(0, requestToUpdate)

            requestFile.saveRequestList(existingRequests)
            _requestListResult.update {
                it.copy(data = existingRequests)
            }
        }
    }

    fun updateRequest(request: Request) {
        viewModelScope.launch {
            val requestList = requestFile.readRequestList()?.toMutableList() ?: return@launch
            val updateRequest = requestList.find { it.id == request.id }

            if (updateRequest == null)
                requestList.add(0, request)
            else {
                val updateRequestIndex = requestList.indexOf(updateRequest)
                requestList[updateRequestIndex] = request
            }

            _requestListResult.update {
                it.copy(data = requestList)
            }
            requestFile.saveRequestList(requestList)
        }
    }

    private fun readRequestList() {
        viewModelScope.launch {
            _requestListResult.update {
                it.copy(state = HTViewModelTaskState.LOADING)
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
}

internal typealias ViewModelTaskResult = HTViewModelTaskResult<List<Request>>
