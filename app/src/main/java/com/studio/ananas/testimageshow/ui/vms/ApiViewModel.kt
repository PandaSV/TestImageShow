package com.studio.ananas.testimageshow.ui.vms

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studio.ananas.testimageshow.api.RetrofitClient
import com.studio.ananas.testimageshow.api.data.ScreenResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ApiViewModel : ViewModel() {

    private val _apiResponse = mutableStateOf<ScreenResponse?>(null)
    val apiResponse: State<ScreenResponse?> = _apiResponse

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun fetchData() {
        _isLoading.value = true
        val apiService = RetrofitClient.apiService()
        viewModelScope.launch {
            apiService.getData().enqueue(object : Callback<ScreenResponse> {
                override fun onResponse(call: Call<ScreenResponse>, response: Response<ScreenResponse>) {
                    if (response.isSuccessful) {
                        _apiResponse.value = response.body()
                    } else {
                        // Handle error
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<ScreenResponse>, t: Throwable) {
                    // Handle failure
                    _isLoading.value = false
                }
            })
        }
    }

//    fun downloadAllFiles(screenResponse: ScreenResponse, outputDirectory: File) {
//        CoroutineScope(Dispatchers.IO).launch {
//            screenResponse.playlists.forEach { playlist ->
//                playlist.playlistItems.forEach { playlistItem ->
//                    // Construct the URL (assuming it's a relative path)
//                    val fileUrl = "https://your-api-endpoint.com/files/" + playlistItem.creativeKey
//                    val fileName = playlistItem.creativeLabel
//
//                    // Call the downloadFile utility function
//                    downloadFile(fileUrl, outputDirectory, fileName)
//                }
//            }
//        }
//    }

    fun downloadAllFiles(screenResponse: ScreenResponse, outputDirectory: File) {
        CoroutineScope(Dispatchers.IO).launch {
            screenResponse.playlists.forEach { playlist ->
                playlist.playlistItems.forEach { playlistItem ->
                    // Construct the full file URL
                    val fileUrl = "https://test.onsignage.com/PlayerBackend/creative/get/" + playlistItem.creativeKey
                    val fileName = playlistItem.creativeLabel

                    // Call the unified API client download function
                    val outputFile = File(outputDirectory, fileName)
                    try {
                        RetrofitClient.downloadFile(fileUrl, outputFile)
                        println("Downloaded: $fileName")
                    } catch (e: Exception) {
                        println("Error downloading: $fileName")
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}
