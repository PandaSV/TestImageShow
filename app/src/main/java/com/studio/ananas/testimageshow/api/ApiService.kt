package com.studio.ananas.testimageshow.api

import com.studio.ananas.testimageshow.api.data.ScreenResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * An interface with the 2 key functions I needed for this assignment
 */
interface ApiService {

    // Gets the screen data
    @Headers("Content-Type: application/json")
    @GET("e490b14d-987d-414f-a822-1e7703b37ce4")
    fun getData(): Call<ScreenResponse>

    // Downloads a file
    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String): ResponseBody
}
