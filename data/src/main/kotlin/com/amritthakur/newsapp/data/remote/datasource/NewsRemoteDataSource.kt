package com.amritthakur.newsapp.data.remote.datasource

import com.amritthakur.newsapp.domain.common.Result
import com.amritthakur.newsapp.domain.entity.NewsParams
import com.amritthakur.newsapp.data.remote.api.NewsApiService
import com.amritthakur.newsapp.data.remote.response.NewsResponse
import com.amritthakur.newsapp.data.remote.response.SourcesResponse
import com.amritthakur.newsapp.data.remote.util.toError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRemoteDataSource @Inject constructor(
    private val newsApiService: NewsApiService
) {

    suspend fun getNews(
        newsParams: NewsParams = NewsParams(),
        pageSize: Int? = null,
        page: Int? = null
    ): Result<NewsResponse> {
        return try {
            val response = newsApiService.getNews(
                country = newsParams.country,
                source = newsParams.source,
                language = newsParams.language,
                pageSize = pageSize,
                page = page
            )

            if (response.isSuccessful) {
                val newsResponse = response.body()
                if (newsResponse != null) {
                    Result.Success(newsResponse)
                } else {
                    Result.Error(-2, "parsingError", "Empty response body")
                }
            } else {
                response.toError()
            }
        } catch (exception: Exception) {
            exception.toError()
        }
    }

    suspend fun getSources(): Result<SourcesResponse> {
        return try {
            val response = newsApiService.getSources()

            if (response.isSuccessful) {
                val sourcesResponse = response.body()
                if (sourcesResponse != null) {
                    Result.Success(sourcesResponse)
                } else {
                    Result.Error(-2, "parsingError", "Empty response body")
                }
            } else {
                response.toError()
            }
        } catch (exception: Exception) {
            exception.toError()
        }
    }

    suspend fun searchNews(
        query: String,
        pageSize: Int? = null,
        page: Int? = null
    ): Result<NewsResponse> {
        return try {
            val response = newsApiService.searchNews(
                query = query,
                pageSize = pageSize,
                page = page
            )

            if (response.isSuccessful) {
                val newsResponse = response.body()
                if (newsResponse != null) {
                    Result.Success(newsResponse)
                } else {
                    Result.Error(-2, "parsingError", "Empty response body")
                }
            } else {
                response.toError()
            }
        } catch (exception: Exception) {
            exception.toError()
        }
    }
}