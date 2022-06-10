package com.amosh.feature.mapper

import com.amosh.common.Mapper
import com.amosh.domain.entity.ComicsEntity
import com.amosh.feature.model.ComicsUiModel
import javax.inject.Inject

class ComicsDomainUiMapper @Inject constructor() :
    Mapper<ComicsEntity, ComicsUiModel> {
    override fun from(i: ComicsEntity?): ComicsUiModel {
        return ComicsUiModel(
            id = i?.id,
            title = i?.title,
            description = i?.description,
            pageCount = i?.pageCount,
            images = i?.images,
            thumbnail = i?.thumbnail,
            languages = i?.languages,
            resourceURI = i?.resourceURI,
            url = i?.url,
            seriesName = i?.seriesName,
            seriesURI = i?.seriesURI,
            price = i?.price
        )
    }

    override fun to(o: ComicsUiModel?): ComicsEntity {
        return ComicsEntity(
            id = o?.id,
            title = o?.title,
            description = o?.description,
            pageCount = o?.pageCount,
            images = o?.images,
            thumbnail = o?.thumbnail,
            languages = o?.languages,
            resourceURI = o?.resourceURI,
            url = o?.url,
            seriesName = o?.seriesName,
            seriesURI = o?.seriesURI,
            price = o?.price
        )
    }
}