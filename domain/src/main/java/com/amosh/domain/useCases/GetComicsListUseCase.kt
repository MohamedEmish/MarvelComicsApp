package com.amosh.domain.useCases

import com.amosh.common.Constants
import com.amosh.common.Resource
import com.amosh.domain.entity.ComicsEntity
import com.amosh.domain.qualifiers.IoDispatcher
import com.amosh.domain.repository.Repository
import com.amosh.domain.useCases.core.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetComicsListUseCase @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : BaseUseCase<List<ComicsEntity>, Map<String, Int>>() {

    override suspend fun buildDetailsRequest(params: Map<String, Int>?): Flow<Resource<List<ComicsEntity>>> {
        return repository.getComicsList(
            limit = params?.get(Constants.LIMIT) ?: Constants.PAGE_SIZE,
            offset = params?.get(Constants.OFFSET) ?: 0
        ).flowOn(dispatcher)
    }
}