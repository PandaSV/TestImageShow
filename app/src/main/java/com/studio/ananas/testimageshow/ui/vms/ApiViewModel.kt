package com.studio.ananas.testimageshow.ui.vms

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studio.ananas.testimageshow.api.RetrofitClient
import com.studio.ananas.testimageshow.api.data.Playlist
import com.studio.ananas.testimageshow.api.data.PlaylistItem
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

    // Download all the media linked in the response
    fun downloadAllFiles(screenResponse: ScreenResponse, outputDirectory: File) {
        CoroutineScope(Dispatchers.IO).launch {
            val newPlaylists: MutableList<Playlist> = screenResponse.playlists.toMutableList()
            screenResponse.playlists.forEachIndexed { playlistindex, playlist ->
                val newPlaylistItems: MutableList<PlaylistItem> = playlist.playlistItems.toMutableList()
                playlist.playlistItems.forEachIndexed { itemIndex, playlistItem ->
                    // TODO move this URL to the API client, make use of the base URL
                    val fileUrl =
                        "https://test.onsignage.com/PlayerBackend/creative/get/" + playlistItem.creativeKey
                    val fileName = playlistItem.creativeLabel

                    // Call the API client download function
                    val outputFile = File(outputDirectory, fileName)
                    try {
                        RetrofitClient.downloadFile(fileUrl, outputFile)
                        newPlaylistItems[itemIndex] = playlistItem.copy(localFilePath = outputFile.absolutePath)

                        println("Downloaded: $fileName")
                    } catch (e: Exception) {
                        println("Error downloading: $fileName")
                        e.printStackTrace()
                    }
                }
                newPlaylists[playlistindex] = playlist.copy(playlistItems = newPlaylistItems)
            }
            _apiResponse.value = screenResponse.copy(playlists = newPlaylists)
        }
    }
}
