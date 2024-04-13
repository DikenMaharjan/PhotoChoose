package com.example.photochoose.feature.choose

import android.net.Uri
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.photochoose.data.photo.model.ImageInfo

const val CHOOSE_PHOTO_ROUTE = "ChoosePhoto"

const val SELECTED_IMAGES_KEY = "SelectedImages"
fun NavGraphBuilder.choosePhotoScreen(
    navigateBack: () -> Unit,
    selectImages: (List<Uri>) -> Unit
) {
    composable(
        route = CHOOSE_PHOTO_ROUTE,
        enterTransition = {
            slideInVertically { it }
        },
        exitTransition = { slideOutVertically { it } }
    ) {
        ChoosePhotoScreen(
            navigateBack = navigateBack,
            selectImages = {
                selectImages(it.map(ImageInfo::uri))
                navigateBack()
            }
        )
    }
}

fun NavController.navigateToChooseImage() {
    this.navigate(CHOOSE_PHOTO_ROUTE)
}