package com.amritthakur.newsapp.di

import com.amritthakur.newsapp.data.remote.datasource.NewsRemoteDataSource
import com.amritthakur.newsapp.domain.repository.NewsRepository
import com.amritthakur.newsapp.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun bindNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource)
    }
}