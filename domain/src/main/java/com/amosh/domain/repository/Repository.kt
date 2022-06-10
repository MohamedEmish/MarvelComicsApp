package com.amosh.domain.repository

import com.amosh.common.Resource
import com.amosh.domain.entity.ComicsEntity
import kotlinx.coroutines.flow.Flow

/**
 * Methods of Repository
 */
interface Repository {
    suspend fun getComicsList(limit: Int, offset: Int): Flow<Resource<List<ComicsEntity>>>
}