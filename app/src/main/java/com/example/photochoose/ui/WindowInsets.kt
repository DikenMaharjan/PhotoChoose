package com.example.photochoose.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberZeroWindowInsets() = remember {
    WindowInsets(0)
}
