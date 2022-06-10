package com.amosh.data.mapper

import com.amosh.common.Mapper
import com.amosh.data.model.ComicsDTO
import com.amosh.domain.entity.ComicsEntity
import javax.inject.Inject

class ComicsDataDomainMapper @Inject constructor() :
    Mapper<ComicsDTO, ComicsEntity> {
    override fun from(i: ComicsDTO?): ComicsEntity {
        return ComicsEntity(
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

    override fun to(o: ComicsEntity?): ComicsDTO {
        return ComicsDTO(
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

