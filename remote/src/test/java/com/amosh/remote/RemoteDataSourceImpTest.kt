package com.amosh.remote

import androidx.test.filters.SmallTest
import com.amosh.data.model.ComicsDTO
import com.amosh.data.repository.RemoteDataSource
import com.amosh.remote.api.ApiService
import com.amosh.remote.mapper.ComicsNetworkResponseMapper
import com.amosh.remote.model.ComicsNetworkResponse
import com.amosh.remote.model.Data
import com.amosh.remote.source.RemoteDataSourceImp
import com.amosh.remote.utils.TestDataGenerator
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class RemoteDataSourceImpTest {

    @MockK
    private lateinit var apiService: ApiService
    private val comicsNetworkResponseMapper = ComicsNetworkResponseMapper()

    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RemoteDataSourceImp before every test
        remoteDataSource = RemoteDataSourceImp(
            apiService = apiService,
            comicsMapper = comicsNetworkResponseMapper,
        )
    }

    @Test
    fun test_get_comics_success() = runBlockingTest {

        val networkResult = ComicsNetworkResponse(data = Data(results = TestDataGenerator.generateComicsItems()))

        // Given
        coEvery { apiService.getComicsList(1, 1) } returns networkResult

        // When
        val result = remoteDataSource.getComicsList(1, 1)

        // Then
        coVerify { apiService.getComicsList(1, 1) }

        // Assertion
        val expectedList: MutableList<ComicsDTO> = mutableListOf()
        networkResult.data?.results?.forEach {
            expectedList.add(comicsNetworkResponseMapper.from(it))
        }
        val expected = expectedList.toList()
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_get_comics_fail() = runBlockingTest {

        // Given
        coEvery { apiService.getComicsList(any()) } throws Exception()

        // When
        remoteDataSource.getComicsList(1, 1)

        // Then
        coVerify { apiService.getComicsList(any()) }

    }
}