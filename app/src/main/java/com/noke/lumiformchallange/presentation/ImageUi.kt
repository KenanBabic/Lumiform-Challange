package com.noke.lumiformchallange.presentation

import kotlinx.serialization.Serializable

@Serializable
data class ImageUi(
    val imageUrl: String,
    val title: String
)
