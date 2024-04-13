package com.example.photochoose.feature.choose

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.photochoose.feature.choose.components.GalleryPicker
import com.example.photochoose.feature.choose.components.ReadPermissionNotGrantedView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChoosePhotoScreen(
    modifier: Modifier = Modifier,
    viewModel: ChoosePhotoScreenViewModel = hiltViewModel()
) {
    val images by viewModel.photos.collectAsState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {

        val readPermissionState = rememberImageReadPermissionState(viewModel::loadPhotos)
        when (readPermissionState.status) {
            is PermissionStatus.Denied -> {
                ReadPermissionNotGrantedView(
                    permissionState = readPermissionState
                )
                LaunchedEffect(readPermissionState) {
                    if (!readPermissionState.status.isGranted) {
                        readPermissionState.launchPermissionRequest()
                    }
                }
            }

            PermissionStatus.Granted -> {
                GalleryPicker(
                    modifier = Modifier
                        .fillMaxSize(),
                    images = images,
                    onImageClick = {
                        if (it.uri != null) {

                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun rememberImageReadPermissionState(
    onGranted: () -> Unit
): PermissionState {
    return rememberPermissionState(
        permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    ) { isGranted ->
        if (isGranted) onGranted()
    }
}


