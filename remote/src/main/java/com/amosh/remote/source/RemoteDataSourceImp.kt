package com.amosh.remote.source

import com.amosh.common.Mapper
import com.amosh.data.model.ComicsDTO
import com.amosh.data.repository.RemoteDataSource
import com.amosh.remote.api.ApiService
import com.amosh.remote.model.ComicsListResults
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(
    private val apiService: ApiService,
    private val comicsMapper: Mapper<ComicsListResults, ComicsDTO>,
) : RemoteDataSource {

    override suspend fun getComicsList(limit: Int, offset: Int): List<ComicsDTO> {
        val networkData = apiService.getComicsList(
            limit = limit,
            offset = offset
        )
        val comicsList: MutableList<ComicsDTO> = mutableListOf()
        networkData.data?.results?.forEach {
            comicsList.add(comicsMapper.from(it))
        }
        return comicsList
    }
}