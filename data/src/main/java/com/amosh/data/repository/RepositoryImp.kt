package com.amosh.data.repository

import android.util.Log
import com.amosh.common.Constants
import com.amosh.common.Mapper
import com.amosh.common.Resource
import com.amosh.data.model.ComicsDTO
import com.amosh.domain.entity.ComicsEntity
import com.amosh.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation class of [Repository]
 */
class RepositoryImp @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val comicsMapper: Mapper<ComicsDTO, ComicsEntity>,
) : Repository {

    override suspend fun getComicsList(
        limit: Int,
        offset: Int,
    ): Flow<Resource<List<ComicsEntity>>> {
        return flow {
            try {
                // Get data from RemoteDataSource
                val data = remoteDataSource.getComicsList(
                    limit = limit,
                    offset = offset
                )
                Log.d("Page number", "${offset / Constants.PAGE_SIZE}")
                // Emit data
                val resultList: MutableList<ComicsEntity> = mutableListOf()
                data.forEach {
                    resultList.add(comicsMapper.from(it))
                }
                //Add to DB
                localDataSource.addItems(data)
                emit(Resource.Success(resultList))
            } catch (ex: Exception) {
                emit(Resource.Error(ex))
                // If remote request fails
                try {
                    // Get data from LocalDataSource
                    val local = localDataSource.getItems()
                    // Emit data
                    val resultList: MutableList<ComicsEntity> = mutableListOf()
                    local.forEach {
                        resultList.add(comicsMapper.from(it))
                    }
                    emit(Resource.Success(resultList))
                } catch (ex1: Exception) {
                    // Emit error
                    emit(Resource.Error(ex1))
                }
            }
        }
    }
}