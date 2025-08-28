package com.amritthakur.newsapp.domain.repository

import androidx.paging.PagingData
import com.amritthakur.newsapp.domain.entity.Article
import com.amritthakur.newsapp.domain.entity.NewsParams
import com.amritthakur.newsapp.domain.entity.Source
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(newsParams: NewsParams = NewsParams()): Flow<PagingData<Article>>

    suspend fun getSources(): Result<List<Source>>

    fun searchNews(query: String): Flow<PagingData<Article>>
}