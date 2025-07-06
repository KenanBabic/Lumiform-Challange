package com.noke.lumiformchallange.presentation

import com.noke.lumiformchallange.domain.model.Item

data class HomeUiState(
    val content: Item? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val lastException: Exception? = null
) {
    private val hasError: Boolean  = errorMessage != null
    val shouldShowRetry: Boolean = hasError
}
