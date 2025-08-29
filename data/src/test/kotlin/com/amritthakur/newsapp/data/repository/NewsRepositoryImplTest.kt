package com.amritthakur.newsapp.data.repository

import com.amritthakur.newsapp.domain.common.Result
import com.amritthakur.newsapp.domain.entity.NewsParams
import com.amritthakur.newsapp.data.remote.datasource.NewsRemoteDataSource
import com.amritthakur.newsapp.data.remote.dto.SourceDto
import com.amritthakur.newsapp.data.remote.response.SourcesResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

class NewsRepositoryImplTest {

    private val mockNewsRemoteDataSource = mockk<NewsRemoteDataSource>()
    private val newsRepository = NewsRepositoryImpl(mockNewsRemoteDataSource)

    // ========== getSources Tests ==========

    @Test
    fun `getSources should return success with mapped sources when remote data source returns success`() =
        runTest {
            // Given
            val sourceDtos = listOf(
                SourceDto(
                    id = "bbc-news",
                    name = "BBC News",
                    description = "BBC News description",
                    url = "http://www.bbc.co.uk/news",
                    category = "general",
                    language = "en",
                    country = "gb"
                ),
                SourceDto(
                    id = "cnn",
                    name = "CNN",
                    description = "CNN description",
                    url = "http://www.cnn.com",
                    category = "general",
                    language = "en",
                    country = "us"
                )
            )
            val sourcesResponse = SourcesResponse(sources = sourceDtos)
            val remoteResult = Result.Success(sourcesResponse)

            coEvery { mockNewsRemoteDataSource.getSources() } returns remoteResult

            // When
            val result = newsRepository.getSources()

            // Then
            assertTrue(result is Result.Success)
            val sources = (result as Result.Success).data
            assertEquals(2, sources.size)

            // Verify mapping worked correctly
            assertEquals("bbc-news", sources[0].id)
            assertEquals("BBC News", sources[0].name)
            assertEquals("BBC News description", sources[0].description)
            assertEquals("http://www.bbc.co.uk/news", sources[0].url)
            assertEquals("general", sources[0].category)
            assertEquals("en", sources[0].language)
            assertEquals("gb", sources[0].country)

            assertEquals("cnn", sources[1].id)
            assertEquals("CNN", sources[1].name)

            coVerify(exactly = 1) { mockNewsRemoteDataSource.getSources() }
        }

    @Test
    fun `getSources should return success with empty list when remote data source returns empty sources`() =
        runTest {
            // Given
            val sourcesResponse = SourcesResponse(sources = emptyList())
            val remoteResult = Result.Success(sourcesResponse)

            coEvery { mockNewsRemoteDataSource.getSources() } returns remoteResult

            // When
            val result = newsRepository.getSources()

            // Then
            assertTrue(result is Result.Success)
            val sources = (result as Result.Success).data
            assertTrue(sources.isEmpty())

            coVerify(exactly = 1) { mockNewsRemoteDataSource.getSources() }
        }

    @Test
    fun `getSources should return success with empty list when remote data source returns null sources`() =
        runTest {
            // Given
            val sourcesResponse = SourcesResponse(sources = null)
            val remoteResult = Result.Success(sourcesResponse)

            coEvery { mockNewsRemoteDataSource.getSources() } returns remoteResult

            // When
            val result = newsRepository.getSources()

            // Then
            assertTrue(result is Result.Success)
            val sources = (result as Result.Success).data
            assertTrue(sources.isEmpty())

            coVerify(exactly = 1) { mockNewsRemoteDataSource.getSources() }
        }

    @Test
    fun `getSources should filter out invalid sources during mapping`() = runTest {
        // Given
        val sourceDtos = listOf(
            // Valid source
            SourceDto(
                id = "bbc-news",
                name = "BBC News",
                description = "BBC News description",
                url = "http://www.bbc.co.uk/news",
                category = "general",
                language = "en",
                country = "gb"
            ),
            // Invalid source - null id
            SourceDto(
                id = null,
                name = "Invalid Source",
                description = "Invalid description",
                url = "http://invalid.com",
                category = "general",
                language = "en",
                country = "us"
            ),
            // Invalid source - blank name
            SourceDto(
                id = "invalid-source",
                name = "   ",
                description = "Invalid description",
                url = "http://invalid.com",
                category = "general",
                language = "en",
                country = "us"
            ),
            // Valid source
            SourceDto(
                id = "cnn",
                name = "CNN",
                description = null, // null optional fields should work
                url = null,
                category = null,
                language = null,
                country = null
            )
        )
        val sourcesResponse = SourcesResponse(sources = sourceDtos)
        val remoteResult = Result.Success(sourcesResponse)

        coEvery { mockNewsRemoteDataSource.getSources() } returns remoteResult

        // When
        val result = newsRepository.getSources()

        // Then
        assertTrue(result is Result.Success)
        val sources = (result as Result.Success).data
        assertEquals(2, sources.size) // Only 2 valid sources should remain

        assertEquals("bbc-news", sources[0].id)
        assertEquals("BBC News", sources[0].name)

        assertEquals("cnn", sources[1].id)
        assertEquals("CNN", sources[1].name)
        assertEquals("", sources[1].description) // null mapped to empty string
        assertEquals("", sources[1].url) // null mapped to empty string

        coVerify(exactly = 1) { mockNewsRemoteDataSource.getSources() }
    }

    @Test
    fun `getSources should return error when remote data source returns error`() = runTest {
        // Given
        val remoteError = Result.Error(
            httpCode = 401,
            errorCode = "apiKeyInvalid",
            errorMessage = "Invalid API key"
        )

        coEvery { mockNewsRemoteDataSource.getSources() } returns remoteError

        // When
        val result = newsRepository.getSources()

        // Then
        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertEquals(401, error.httpCode)
        assertEquals("apiKeyInvalid", error.errorCode)
        assertEquals("Invalid API key", error.errorMessage)

        coVerify(exactly = 1) { mockNewsRemoteDataSource.getSources() }
    }

    @Test
    fun `getSources should pass through parameterInvalid error unchanged`() = runTest {
        // Given
        val expectedError = Result.Error(400, "parameterInvalid", "Invalid parameter")
        coEvery { mockNewsRemoteDataSource.getSources() } returns expectedError

        // When
        val result = newsRepository.getSources()

        // Then
        assertEquals(expectedError, result)
        coVerify(exactly = 1) { mockNewsRemoteDataSource.getSources() }
    }

    @Test
    fun `getSources should pass through rateLimited error unchanged`() = runTest {
        // Given
        val expectedError = Result.Error(429, "rateLimited", "Rate limit exceeded")
        coEvery { mockNewsRemoteDataSource.getSources() } returns expectedError

        // When
        val result = newsRepository.getSources()

        // Then
        assertEquals(expectedError, result)
        coVerify(exactly = 1) { mockNewsRemoteDataSource.getSources() }
    }

    @Test
    fun `getSources should pass through serverError unchanged`() = runTest {
        // Given
        val expectedError = Result.Error(500, "serverError", "Internal server error")
        coEvery { mockNewsRemoteDataSource.getSources() } returns expectedError

        // When
        val result = newsRepository.getSources()

        // Then
        assertEquals(expectedError, result)
        coVerify(exactly = 1) { mockNewsRemoteDataSource.getSources() }
    }

    @Test
    fun `getSources should pass through network error unchanged`() = runTest {
        // Given
        val expectedError = Result.Error(-1, "IOException", "Network error")
        coEvery { mockNewsRemoteDataSource.getSources() } returns expectedError

        // When
        val result = newsRepository.getSources()

        // Then
        assertEquals(expectedError, result)
        coVerify(exactly = 1) { mockNewsRemoteDataSource.getSources() }
    }

    // ========== getNews Tests ==========
    // Note: getNews creates a Pager with PagingConfig and NewsPagingSource.
    // The actual paging logic and parameter passing will be tested in NewsPagingSource tests.
    // Here we only verify that the method doesn't crash and returns a Flow.

    @Test
    fun `getNews should return non-null Flow for any NewsParams`() {
        // Given
        val testParams = listOf(
            NewsParams(), // default params
            NewsParams(country = "us"),
            NewsParams(source = "bbc-news"),
            NewsParams(language = "en"),
            NewsParams(country = "us", source = "bbc-news", language = "en")
        )

        testParams.forEach { params ->
            // When
            val result = newsRepository.getNews(params)

            // Then
            assertNotNull("Failed for params: $params", result)
        }
    }

    // ========== searchNews Tests ==========
    // Note: searchNews creates a Pager with PagingConfig and SearchNewsPagingSource.
    // The actual paging logic and query handling will be tested in SearchNewsPagingSource tests.
    // Here we only verify that the method doesn't crash and returns a Flow.

    @Test
    fun `searchNews should return non-null Flow for any query`() {
        // Given
        val testQueries = listOf(
            "android development",
            "",
            "covid-19 & vaccine news!",
            "special chars !@#$%"
        )

        testQueries.forEach { query ->
            // When
            val result = newsRepository.searchNews(query)

            // Then
            assertNotNull("Failed for query: '$query'", result)
        }
    }
}