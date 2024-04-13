package com.example.photochoose.data.photo.model

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class ImageInfo(
    val uri: Uri,
    val title: String?,
    val displayName: String?
)
