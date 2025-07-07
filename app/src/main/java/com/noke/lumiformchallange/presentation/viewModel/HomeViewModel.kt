package com.noke.lumiformchallange.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noke.lumiformchallange.data.Result.Error
import com.noke.lumiformchallange.data.Result.Success
import com.noke.lumiformchallange.data.remote.NetworkConnectivityChecker
import com.noke.lumiformchallange.domain.repository.ItemRepository
import com.noke.lumiformchallange.presentation.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    networkConnectivityChecker: NetworkConnectivityChecker
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = combine(
        _uiState.asStateFlow(),
        networkConnectivityChecker.observeConnectivity()
    ) { state, isConnected ->
        state.copy(isConnected = isConnected)
    }.onStart {
        fetchItems()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        HomeUiState()
    )

    fun onRetryAction() {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = itemRepository.getItems()) {
                is Success -> {
                    _uiState.update { lastState ->
                        lastState.copy(
                            content = result.data,
                            isLoading = false,
                        )
                    }
                }

                is Error -> {
                    _uiState.update { lastState ->
                        lastState.copy(
                            isLoading = false,
                            errorMessage = result.exception.message ?: "Failed to load content",
                        )
                    }
                }
            }
        }
    }

}