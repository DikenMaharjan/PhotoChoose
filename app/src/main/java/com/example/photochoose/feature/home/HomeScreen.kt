package com.example.photochoose.feature.home

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navigateToChooseImage: () -> Unit,
    selectedImages: List<Uri>
) {
    LaunchedEffect(selectedImages) {
        viewModel.setSelectedImages(selectedImages)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Browse Screen")
        Button(onClick = navigateToChooseImage) {
            Text(text = "Choose Image")
        }
    }
}