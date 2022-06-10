package com.amosh.remote.api

import com.amosh.common.Constants
import com.amosh.remote.BuildConfig
import com.amosh.remote.model.ComicsNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigInteger
import java.security.MessageDigest

interface ApiService {

    @GET("comics")
    suspend fun getComicsList(
        @Query(Constants.LIMIT) limit: Int = Constants.PAGE_SIZE,
        @Query(Constants.OFFSET) offset: Int = 0,
        @Query(Constants.TIME_STAMP) timeStamp: Long = System.currentTimeMillis(),
        @Query(Constants.API_KEY) apiKey: String = BuildConfig.PUBLIC_KEY,
        @Query(Constants.HASH) hash: String = "${timeStamp}${BuildConfig.PRIVATE_KEY}${BuildConfig.PUBLIC_KEY}".md5(),
    ): ComicsNetworkResponse

    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }
}