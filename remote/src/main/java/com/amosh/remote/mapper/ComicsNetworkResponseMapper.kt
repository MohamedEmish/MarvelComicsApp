package com.amosh.remote.mapper

import com.amosh.common.Mapper
import com.amosh.common.toHttps
import com.amosh.data.model.ComicsDTO
import com.amosh.remote.model.ComicsListResults
import javax.inject.Inject

class ComicsNetworkResponseMapper @Inject constructor() :
    Mapper<ComicsListResults, ComicsDTO> {
    override fun from(i: ComicsListResults?): ComicsDTO {
        return ComicsDTO(
            id = i?.id,
            title = i?.title,
            description = i?.description,
            pageCount = i?.pageCount,
            images = i?.images?.map { "${it.path?.toHttps()}.${it.extension}" },
            thumbnail = "${i?.thumbnail?.path?.toHttps()}.${i?.thumbnail?.extension}",
            languages = i?.textObjects?.map { it.language ?: "" },
            resourceURI = i?.resourceURI,
            url = i?.urls?.get(0)?.url,
            seriesName = i?.series?.name,
            seriesURI = i?.series?.resourceURI,
            price = i?.prices?.get(0)?.price,
        )
    }

    override fun to(o: ComicsDTO?): ComicsListResults {
        return ComicsListResults()
    }

}
