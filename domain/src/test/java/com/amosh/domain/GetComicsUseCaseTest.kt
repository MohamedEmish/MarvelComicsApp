package com.amosh.domain

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.amosh.common.Constants
import com.amosh.common.Resource
import com.amosh.domain.repository.Repository
import com.amosh.domain.useCases.GetComicsListUseCase
import com.amosh.domain.utils.MainCoroutineRule
import com.amosh.domain.utils.TestDataGenerator
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class GetComicsListUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var repository: Repository

    private lateinit var getComicsListUseCase: GetComicsListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        getComicsListUseCase = GetComicsListUseCase(
            repository = repository,
            dispatcher = mainCoroutineRule.dispatcher
        )
    }

    @Test
    fun test_get_comics_success() = runBlockingTest {

        val comicsItems = TestDataGenerator.generateComicsItems()
        val comicFlow = flowOf(Resource.Success(comicsItems))

        // Given
        coEvery { repository.getComicsList(1, 1) } returns comicFlow

        // When & Assertions
        val result = getComicsListUseCase.execute(
            mapOf(
                Constants.LIMIT to Constants.PAGE_SIZE,
                Constants.OFFSET to 1
            )
        )
        result.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData).isEqualTo(comicsItems)
            expectComplete()
        }

        // Then
        coVerify { repository.getComicsList(1, 1) }

    }

    @Test
    fun test_get_comics_fail_pass_parameter_with_null() = runBlockingTest {

        val errorFlow = flowOf(Resource.Error(Exception()))

        // When & Assertions
        val result = getComicsListUseCase.execute(null)
        result.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(Resource.Error::class.java)
            expectComplete()
        }

        // Then
//        coVerify { repository.getComicsList(1, 1) }

    }
}