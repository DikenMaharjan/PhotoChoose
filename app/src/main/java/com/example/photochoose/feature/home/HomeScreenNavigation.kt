package com.example.photochoose.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "HomeRoute"

fun NavGraphBuilder.homeScreen(navigateToChooseImage: () -> Unit) {
    composable(HOME_ROUTE) {
        HomeScreen(
            navigateToChooseImage = navigateToChooseImage
        )
    }

}