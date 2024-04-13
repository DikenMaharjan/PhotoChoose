package com.example.photochoose.feature.choose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.photochoose.data.photo.model.ImageInfo
import com.example.photochoose.ui.theme.LocalSpacing
import com.example.photochoose.ui.theme.PhotoChooseTheme

@Composable
fun GalleryPicker(
    modifier: Modifier = Modifier,
    images: List<ImageInfo>,
    onImageClick: (ImageInfo) -> Unit,
    selectedImages: List<ImageInfo>
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
                    image = image,
                    isSelected = remember(
                        image, selectedImages
                    ) {
                        image in selectedImages
                    }
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
    image: ImageInfo,
    isSelected: Boolean
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
        SelectedIcon(
            modifier = Modifier
                .padding(LocalSpacing.current.dimen12)
                .align(Alignment.BottomEnd),
            isSelected = isSelected
        )
    }
}

@Composable
private fun SelectedIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean
) {
    val borderColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val combinedModifier = modifier
        .border(
            width = LocalSpacing.current.dimen2,
            color = borderColor,
            shape = CircleShape
        )
        .clip(CircleShape)
    if (isSelected) {
        Icon(
            modifier = combinedModifier
                .background(MaterialTheme.colorScheme.primary),
            imageVector = Icons.Filled.Check,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        Box(
            modifier = combinedModifier
                .size(LocalSpacing.current.dimen24)
        )
    }

}

@Preview
@Composable
private fun SelectedIconPreview() {
    PhotoChooseTheme {
        Column {
            SelectedIcon(isSelected = true)
            SelectedIcon(isSelected = false)
        }
    }
}