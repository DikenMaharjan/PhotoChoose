package com.example.photochoose.core.model

import kotlinx.coroutines.CoroutineDispatcher

data class AppDispatchers(
    val default: CoroutineDispatcher,
    val background: CoroutineDispatcher,
)