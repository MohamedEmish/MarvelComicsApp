package com.amosh.remote.model

data class Stories(
    val available: String? = null,
    val returned: String? = null,
    val collectionURI: String? = null,
    val items: List<Items>? = null,
)