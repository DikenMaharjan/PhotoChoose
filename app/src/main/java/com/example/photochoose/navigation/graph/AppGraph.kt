package com.example.photochoose.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.photochoose.feature.choose.choosePhotoScreen
import com.example.photochoose.feature.choose.navigateToChooseImage
import com.example.photochoose.feature.home.HOME_ROUTE
import com.example.photochoose.feature.home.homeScreen

const val ROOT = "root"
fun NavGraphBuilder.appGraph(
    navController: NavController
) {
    navigation(
        route = ROOT,
        startDestination = HOME_ROUTE
    ) {
        homeScreen(navigateToChooseImage = navController::navigateToChooseImage)
        choosePhotoScreen(
            navigateBack = navController::navigateUp
        )
    }

}