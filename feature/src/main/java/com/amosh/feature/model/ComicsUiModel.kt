package com.amosh.feature.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicsUiModel(
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
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ComicsUiModel

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (pageCount != other.pageCount) return false
        if (images != other.images) return false
        if (thumbnail != other.thumbnail) return false
        if (languages != other.languages) return false
        if (resourceURI != other.resourceURI) return false
        if (url != other.url) return false
        if (seriesName != other.seriesName) return false
        if (seriesURI != other.seriesURI) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (pageCount?.hashCode() ?: 0)
        result = 31 * result + (images?.hashCode() ?: 0)
        result = 31 * result + (thumbnail?.hashCode() ?: 0)
        result = 31 * result + (languages?.hashCode() ?: 0)
        result = 31 * result + (resourceURI?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (seriesName?.hashCode() ?: 0)
        result = 31 * result + (seriesURI?.hashCode() ?: 0)
        result = 31 * result + (price?.hashCode() ?: 0)
        return result
    }
}

