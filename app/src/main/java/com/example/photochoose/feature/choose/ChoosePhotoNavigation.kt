package com.example.photochoose.feature.choose

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val CHOOSE_PHOTO_ROUTE = "ChoosePhoto"
fun NavGraphBuilder.choosePhotoScreen() {
    composable(CHOOSE_PHOTO_ROUTE) {
        ChoosePhotoScreen()
    }
}

fun NavController.navigateToChooseImage() {
    this.navigate(CHOOSE_PHOTO_ROUTE)
}