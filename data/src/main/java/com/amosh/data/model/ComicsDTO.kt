package com.amosh.data.model

data class ComicsDTO(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val pageCount: String? = null,
    val images: List<String>? = null,
    val thumbnail: String? = null,
    val languages: List<String>? = null,
    val resourceURI: String? = null,
    val url: String? = null,
    val seriesName: String? = null,
    val seriesURI: String? = null,
    val price: String? = null,
)
