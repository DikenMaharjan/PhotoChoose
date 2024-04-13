package com.example.photochoose.feature.home.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.photochoose.ui.theme.LocalSpacing

@Composable
fun ImageView(
    modifier: Modifier = Modifier,
    uri: Uri,
    index: Int
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = uri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = remember {
                ColorPainter(Color.Gray)
            }
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        0.74f to Color.Transparent,
                        1f to Color.Black.copy(alpha = 0.75f),
                    )
                )
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(LocalSpacing.current.dimen2),
                text = (index + 1).toString(),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )
        }
    }
}
