package com.amosh.local

import androidx.test.filters.SmallTest
import com.amosh.data.repository.LocalDataSource
import com.amosh.local.database.ComicsDAO
import com.amosh.local.mapper.ComicsLocalDataMapper
import com.amosh.local.source.LocalDataSourceImp
import com.amosh.local.utils.TestData
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
class LocalDataSourceImpTest {

    @MockK
    private lateinit var comicsDAO: ComicsDAO

    private val comicsLocalDataMapper = ComicsLocalDataMapper()
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create LocalDataSourceImp before every test
        localDataSource = LocalDataSourceImp(
            comicsDAO = comicsDAO,
            comicsMapper = comicsLocalDataMapper
        )
    }

    @Test(expected = Exception::class)
    fun test_add_comics_item_fail() = runBlockingTest {

        val comicsItem = comicsLocalDataMapper.from(i = TestData.generateComicItem())

        // Given
        coEvery { comicsDAO.addComicsItem(any()) } throws Exception()

        // When
        localDataSource.addItem(comicsItem)

        // Then
        coVerify { comicsDAO.addComicsItem(any()) }

    }

    @Test
    fun test_get_comics_item_success() = runBlockingTest {

        val comicsLocal = TestData.generateComicItem()
        val expected = comicsLocalDataMapper.from(i = comicsLocal)

        // Given
        coEvery { comicsDAO.getComicsItemById(any()) } returns comicsLocal

        // When
        val returned = localDataSource.getItemById(comicsLocal.id.toString())

        // Then
        coVerify { comicsDAO.getComicsItemById(any()) }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_get_comics_item_fail() = runBlockingTest {

        val comicsItem = comicsLocalDataMapper.from(i = TestData.generateComicItem())

        // Given
        coEvery { comicsDAO.getComicsItemById(any()) } throws Exception()

        // When
        localDataSource.getItemById(comicsItem.id.toString())

        // Then
        coVerify { comicsDAO.getComicsItemById(any()) }

    }

    @Test(expected = Exception::class)
    fun test_add_comics_items_fail() = runBlockingTest {

        val comicsItems = comicsLocalDataMapper.fromList(list = TestData.generateComicItems())

        // Given
        coEvery { comicsDAO.addComicsItems(any()) } throws Exception()

        // When
        localDataSource.addItems(comicsItems)

        // Then
        coVerify { comicsDAO.addComicsItems(any()) }

    }

    @Test
    fun test_get_comics_items_success() = runBlockingTest {

        val comicsItems = TestData.generateComicItems()
        val expected = comicsLocalDataMapper.fromList(list = comicsItems)

        // Given
        coEvery { comicsDAO.getComicsItems() } returns comicsItems

        // When
        val returned = localDataSource.getItems()

        // Then
        coVerify { comicsDAO.getComicsItems() }

        // Assertion
        Truth.assertThat(returned).containsExactlyElementsIn(expected)
    }

    @Test(expected = Exception::class)
    fun test_get_comics_items_fail() = runBlockingTest {

        // Given
        coEvery { comicsDAO.getComicsItems() } throws Exception()

        // When
        localDataSource.getItems()

        // Then
        coVerify { comicsDAO.getComicsItems() }

    }

    @Test(expected = Exception::class)
    fun test_update_comics_item_fail() = runBlockingTest {

        val comicsItem = comicsLocalDataMapper.from(i = TestData.generateComicItem())

        // Given
        coEvery { comicsDAO.updateComicsItem(any()) } throws Exception()

        // When
        localDataSource.updateItem(comicsItem)

        // Then
        coVerify { comicsDAO.updateComicsItem(any()) }

    }

    @Test(expected = Exception::class)
    fun test_delete_comics_item_by_id_fail() = runBlockingTest {

        // Given
        coEvery { comicsDAO.deleteComicsItem(any()) } throws Exception()

        // When
        localDataSource.deleteItemById(1)

        // Then
        coVerify { comicsDAO.deleteComicsItem(any()) }

    }

    @Test(expected = Exception::class)
    fun test_delete_comics_item_fail() = runBlockingTest {

        val comicsItem = comicsLocalDataMapper.from(i = TestData.generateComicItem())

        // Given
        coEvery { comicsDAO.deleteComicsItem(any()) } throws Exception()

        // When
        localDataSource.deleteItem(comicsItem)

        // Then
        coVerify { comicsDAO.deleteComicsItem(any()) }

    }

    @Test
    fun test_clear_all_comics_success() = runBlockingTest {

        val comicsItems = comicsLocalDataMapper.fromList(list = TestData.generateComicItems())
        val expected = comicsItems.size // Affected row count

        // Given
        coEvery { comicsDAO.clearCachedComicsItems() } returns expected

        // When
        val returned = localDataSource.clearCachedItems()

        // Then
        coVerify { comicsDAO.clearCachedComicsItems() }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)

    }

    @Test(expected = Exception::class)
    fun test_clear_all_comics_fail() = runBlockingTest {

        // Given
        coEvery { comicsDAO.clearCachedComicsItems() } throws Exception()

        // When
        localDataSource.clearCachedItems()

        // Then
        coVerify { comicsDAO.clearCachedComicsItems() }

    }
}