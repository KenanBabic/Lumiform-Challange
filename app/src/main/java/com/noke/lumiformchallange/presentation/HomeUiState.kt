package com.noke.lumiformchallange.presentation

import com.noke.lumiformchallange.domain.model.Item

data class HomeUiState(
    val content: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val lastException: Exception? = null,
    val isConnected: Boolean = true
) {
    private val hasError: Boolean  = errorMessage != null
    val shouldShowRetry: Boolean = hasError
}
