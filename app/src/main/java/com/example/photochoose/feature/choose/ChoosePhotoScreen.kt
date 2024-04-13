package com.example.photochoose.feature.choose

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.photochoose.data.photo.model.ImageInfo
import com.example.photochoose.feature.choose.components.GalleryPicker
import com.example.photochoose.feature.choose.components.ReadPermissionNotGrantedView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChoosePhotoScreen(
    modifier: Modifier = Modifier,
    viewModel: ChoosePhotoScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    selectImages: (List<ImageInfo>) -> Unit
) {
    val images by viewModel.photos.collectAsStateWithLifecycle()
    val selectedImages by viewModel.selectedPhotos.collectAsStateWithLifecycle()
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
            }

            PermissionStatus.Granted -> {
                ChoosePhotoScreenContent(
                    images = images,
                    onBack = navigateBack,
                    selectDisSelectImage = viewModel::selectDisSelectImage,
                    selectedImages = selectedImages,
                    onDone = {
                        selectImages(selectedImages)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChoosePhotoScreenContent(
    modifier: Modifier = Modifier,
    images: List<ImageInfo>,
    onBack: () -> Unit,
    selectedImages: List<ImageInfo>,
    selectDisSelectImage: (ImageInfo) -> Unit,
    onDone: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Choose Images (${selectedImages.size}/${ChoosePhotoScreenViewModel.NUM_OF_PHOTO_TO_CHOOSE})")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Icon"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = onDone,
                        enabled = selectedImages.size == ChoosePhotoScreenViewModel.NUM_OF_PHOTO_TO_CHOOSE
                    ) {
                        Text(text = "Done")
                    }
                }
            )
        }
    ) { padding ->
        GalleryPicker(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            images = images,
            onImageClick = selectDisSelectImage,
            selectedImages = selectedImages
        )
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


