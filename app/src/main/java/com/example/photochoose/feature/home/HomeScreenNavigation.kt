package com.example.photochoose.feature.home

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.photochoose.feature.choose.SELECTED_IMAGES_KEY

const val HOME_ROUTE = "HomeRoute"

fun NavGraphBuilder.homeScreen(navigateToChooseImage: () -> Unit) {
    composable(HOME_ROUTE) {
        val selectedImages by it.savedStateHandle
            .getStateFlow(SELECTED_IMAGES_KEY, listOf<Uri>())
            .collectAsStateWithLifecycle()
        HomeScreen(
            navigateToChooseImage = navigateToChooseImage,
            selectedImages = selectedImages
        )
    }
}