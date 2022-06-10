package com.amosh.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comics")
data class ComicsLocalModel(
    @PrimaryKey
    val id: Int,
    val comicId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val pageCount: String? = null,
    val image: String? = null,
    val thumbnail: String? = null,
    val language: String? = null,
    val resourceURI: String? = null,
    val url: String? = null,
    val seriesName: String? = null,
    val seriesURI: String? = null,
    val price: String? = null,
)