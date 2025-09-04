package com.amritthakur.newsapp.di

import com.amritthakur.newsapp.data.local.datasource.NewsLocalDataSource
import com.amritthakur.newsapp.data.remote.datasource.NewsRemoteDataSource
import com.amritthakur.newsapp.data.repository.NewsRepositoryImpl
import com.amritthakur.newsapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }
}
