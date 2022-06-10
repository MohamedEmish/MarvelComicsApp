package com.amosh.data.repository

import com.amosh.data.model.ComicsDTO


/**
 * Methods of Remote Data Source
 */
interface RemoteDataSource {
    suspend fun getComicsList(limit:Int, offset: Int): List<ComicsDTO>
}