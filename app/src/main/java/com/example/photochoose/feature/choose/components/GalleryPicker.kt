package com.example.photochoose.feature.choose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.photochoose.data.photo.model.ImageInfo
import com.example.photochoose.ui.theme.LocalSpacing

@Composable
fun GalleryPicker(
    modifier: Modifier = Modifier,
    images: List<ImageInfo>,
    onImageClick: (ImageInfo) -> Unit
) {
    if (images.isEmpty()) {
        NoImagesView()
    } else {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(3)
        ) {
            items(images) { image ->
                ImageView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(LocalSpacing.current.dimen2)
                        .clickable {
                            onImageClick(image)
                        },
                    image = image
                )
            }
        }
    }
}

@Composable
private fun NoImagesView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "No Images")
    }
}

@Composable
private fun ImageView(
    modifier: Modifier = Modifier,
    image: ImageInfo
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = image.uri,
            contentDescription = image.title,
            contentScale = ContentScale.Crop,
            placeholder = remember {
                ColorPainter(Color.Gray)
            }
        )
    }
}
