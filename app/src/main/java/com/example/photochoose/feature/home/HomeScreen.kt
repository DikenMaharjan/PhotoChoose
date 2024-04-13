package com.example.photochoose.feature.home

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.photochoose.feature.home.components.EditResultSizeSheet
import com.example.photochoose.feature.home.components.ImageView
import com.example.photochoose.ui.rememberZeroWindowInsets
import com.example.photochoose.ui.theme.LocalSpacing
import kotlin.math.sqrt

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navigateToChooseImage: () -> Unit,
    selectedImagesUri: List<Uri>
) {
    if (selectedImagesUri.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                modifier = Modifier, onClick = navigateToChooseImage
            ) {
                Text(text = "Choose Images")
            }
        }
    } else {
        val resultCount by viewModel.resultCount.collectAsStateWithLifecycle()
        HomeScreenImageSelectedContent(
            modifier = modifier,
            resultCount = resultCount,
            selectedImagesUri = selectedImagesUri,
            setResultCount = viewModel::setResultCount,
            navigateToChooseImage = navigateToChooseImage
        )
    }
}

@Composable
private fun HomeScreenImageSelectedContent(
    modifier: Modifier,
    resultCount: Int,
    selectedImagesUri: List<Uri>,
    setResultCount: (Int) -> Unit,
    navigateToChooseImage: () -> Unit
) {
    var isEditSheetVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize().systemBarsPadding(),
        topBar = {
            HomeScreenTopBar(
                showEditSheet = { isEditSheetVisible = true }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = navigateToChooseImage) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Choose More Image")
                Spacer(modifier = Modifier.widthIn(LocalSpacing.current.dimen4))
                Text(text = "Choose")
            }
        },
        contentWindowInsets = rememberZeroWindowInsets()
    ) { padding ->
        SelectedImages(
            modifier = Modifier.padding(padding),
            resultCount = resultCount,
            selectedImagesUri = selectedImagesUri
        )
        if (isEditSheetVisible) {
            EditResultSizeSheet(
                hideEditSheet = {
                    isEditSheetVisible = false
                },
                setResultSize = setResultCount
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeScreenTopBar(showEditSheet: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Chosen Results") },
        actions = {
            IconButton(
                onClick = showEditSheet
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Result Size"
                )
            }
        }
    )
}

@Composable
private fun SelectedImages(
    modifier: Modifier,
    resultCount: Int,
    selectedImagesUri: List<Uri>
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(3),
    ) {
        items(resultCount) { index ->
            val isTriangular = remember(index) { (index + 1).isTriangular() }
            ImageView(
                modifier = Modifier.padding(LocalSpacing.current.dimen2),
                uri = if (isTriangular) selectedImagesUri[0] else selectedImagesUri[1],
                index = index
            )
        }
    }
}


private fun Int.isTriangular(): Boolean {
    return sqrt((8 * this + 1).toDouble()).run {
        this == this.toInt().toDouble()
    }
}