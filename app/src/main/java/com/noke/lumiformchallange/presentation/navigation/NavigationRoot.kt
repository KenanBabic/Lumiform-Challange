package com.noke.lumiformchallange.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.noke.lumiformchallange.presentation.ImageUi
import com.noke.lumiformchallange.presentation.screen.HomeScreen
import com.noke.lumiformchallange.presentation.screen.ImageDetailScreen

@Composable
fun NavigationRoot(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onImageClick = { imageUrl, title ->
                    val imageDetails = ImageUi(imageUrl, title)
                    navController.navigate(Screen.ImageDetail.createRoute(imageDetails))
                }
            )
        }

        composable(
            route = Screen.ImageDetail.route,
            arguments = listOf(
                navArgument("imageDetails") {
                    type = CustomNavType.ImageDetails
                }
            )
        ) { backStackEntry ->
            val imageDetails = backStackEntry.arguments?.let {
                CustomNavType.ImageDetails[it, "imageDetails"]
            } ?: ImageUi("", "")

            ImageDetailScreen(
                imageUrl = imageDetails.imageUrl,
                title = imageDetails.title,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}