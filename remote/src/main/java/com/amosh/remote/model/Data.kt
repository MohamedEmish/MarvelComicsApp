package com.amosh.remote.model

data class Data(
    val offset: String? = null,
    val limit: String? = null,
    val total: String? = null,
    val count: String? = null,
    val results: List<ComicsListResults>? = null,
)