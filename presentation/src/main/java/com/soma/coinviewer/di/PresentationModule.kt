package com.soma.coinviewer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PresentationModule {
    @Binds
    @Singleton
    fun bindsCoroutineExceptionHandler(
        baseCoroutineExceptionHandler: BaseCoroutineExceptionHandler
    ): CoroutineExceptionHandler
}