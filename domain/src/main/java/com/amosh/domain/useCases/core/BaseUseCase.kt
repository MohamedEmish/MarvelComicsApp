package com.amosh.domain.useCases.core

import com.amosh.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Base Use Case class
 */
abstract class BaseUseCase<Model, Params> {

    abstract suspend fun buildDetailsRequest(
        params: Params?
    ): Flow<Resource<Model>>

    suspend fun execute(type: Params?): Flow<Resource<Model>> {
        return try {
            buildDetailsRequest(type)
        } catch (exception: Exception) {
            flow { emit(Resource.Error(exception)) }
        }
    }
}