package com.amosh.data.repository

import com.amosh.data.model.ComicsDTO


/**
 * Methods of Local Data Source
 */
interface LocalDataSource {
    suspend fun addItem(comics: ComicsDTO): Long

    suspend fun getItemByTitle(title: String): ComicsDTO

    suspend fun getItemById(comicsId: String): ComicsDTO

    suspend fun addItems(comics: List<ComicsDTO>): List<Long>

    suspend fun getItems(): List<ComicsDTO>

    suspend fun updateItem(comics: ComicsDTO): Int

    suspend fun deleteItemById(id: Int): Int

    suspend fun deleteItem(comics: ComicsDTO): Int

    suspend fun clearCachedItems(): Int
}