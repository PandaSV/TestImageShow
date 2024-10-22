package com.studio.ananas.testimageshow.api

import com.studio.ananas.testimageshow.api.data.ScreenResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("e490b14d-987d-414f-a822-1e7703b37ce4")
    fun getData(): Call<ScreenResponse>
}
