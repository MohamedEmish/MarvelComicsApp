package com.amosh.marvelcomicsapp.di

import com.amosh.common.Mapper
import com.amosh.data.mapper.ComicsDataDomainMapper
import com.amosh.data.model.ComicsDTO
import com.amosh.domain.entity.ComicsEntity
import com.amosh.feature.mapper.ComicsDomainUiMapper
import com.amosh.feature.model.ComicsUiModel
import com.amosh.local.mapper.ComicsLocalDataMapper
import com.amosh.local.model.ComicsLocalModel
import com.amosh.remote.mapper.ComicsNetworkResponseMapper
import com.amosh.remote.model.ComicsListResults
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module that holds Mappers
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    //region Locale Mappers
    @Binds
    abstract fun bindsComicsLocalDataMapper(mapper: ComicsLocalDataMapper): Mapper<ComicsLocalModel, ComicsDTO>
    //endregion

    //region Data Mappers
    @Binds
    abstract fun bindsComicsDataDomainMapper(mapper: ComicsDataDomainMapper): Mapper<ComicsDTO, ComicsEntity>
    //endregion

    //region Presentation Mappers
    @Binds
    abstract fun bindsComicsDomainUiMapper(mapper: ComicsDomainUiMapper): Mapper<ComicsEntity, ComicsUiModel>
    //endregion

    //region Remote Mappers
    @Binds
    abstract fun bindsComicsNetworkDataMapper(mapper: ComicsNetworkResponseMapper): Mapper<ComicsListResults, ComicsDTO>
    //endregion

}