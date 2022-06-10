package com.amosh.local.mapper

import com.amosh.common.Mapper
import com.amosh.data.model.ComicsDTO
import com.amosh.local.model.ComicsLocalModel
import javax.inject.Inject

class ComicsLocalDataMapper @Inject constructor() :
    Mapper<ComicsLocalModel, ComicsDTO> {
    override fun from(i: ComicsLocalModel?): ComicsDTO {
        return ComicsDTO(
            id = i?.comicId,
            title = i?.title,
            description = i?.description,
            pageCount = i?.pageCount,
            images = if (i?.image.isNullOrEmpty()) mutableListOf() else listOf(i?.image!!),
            thumbnail = i?.thumbnail,
            languages = if (i?.language.isNullOrEmpty()) mutableListOf() else listOf(i?.language!!),
            resourceURI = i?.resourceURI,
            url = i?.url,
            seriesName = i?.seriesName,
            seriesURI = i?.seriesURI,
            price = i?.price
        )
    }

    override fun to(o: ComicsDTO?): ComicsLocalModel {
        return ComicsLocalModel(
            id = 0,
            comicId = o?.id,
            title = o?.title,
            description = o?.description,
            pageCount = o?.pageCount,
            image = o?.images?.get(0),
            thumbnail = o?.thumbnail,
            language = o?.languages?.get(0),
            resourceURI = o?.resourceURI,
            url = o?.url,
            seriesName = o?.seriesName,
            seriesURI = o?.seriesURI,
            price = o?.price
        )
    }
}