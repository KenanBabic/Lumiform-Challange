package com.noke.lumiformchallange.presentation.navigation

import com.noke.lumiformchallange.presentation.ImageUi

sealed class Screen(val route: String) {
    data object Home : Screen("home")

    data object ImageDetail : Screen("image_detail/{imageDetails}") {
        fun createRoute(imageDetails: ImageUi): String {
            return "image_detail/${CustomNavType.ImageDetails.serializeAsValue(imageDetails)}"
        }
    }
}