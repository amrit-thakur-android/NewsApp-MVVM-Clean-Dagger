package com.amritthakur.newsapp.di

import com.amritthakur.newsapp.presentation.common.DefaultDispatcherProvider
import com.amritthakur.newsapp.presentation.common.DispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }
}
