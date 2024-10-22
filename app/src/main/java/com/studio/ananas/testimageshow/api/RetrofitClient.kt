package com.studio.ananas.testimageshow.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.FileOutputStream

/**
 * API Client
 */
object RetrofitClient {

    private const val BASE_URL = "https://test.onsignage.com/PlayerBackend/screen/playlistItems/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun apiService(): ApiService = instance.create(ApiService::class.java)

    // Download a single file
    suspend fun downloadFile(fileUrl: String, outputFile: File) {
        val responseBody: ResponseBody = apiService().downloadFile(fileUrl)
        responseBody.byteStream().use { inputStream ->
            FileOutputStream(outputFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}
