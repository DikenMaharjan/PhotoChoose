package com.example.photochoose.core.di

import com.example.photochoose.core.model.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CoreModule {
    companion object {
        @Provides
        @Singleton
        fun providesDispatchers(): AppDispatchers {
            return AppDispatchers(
                default = Dispatchers.Default,
                background = Dispatchers.IO
            )
        }

        @Provides
        @Singleton
        @ApplicationScope
        fun providesCoroutineScope(
            dispatcher: AppDispatchers,
        ): CoroutineScope = CoroutineScope(dispatcher.default + SupervisorJob())

    }

}


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope