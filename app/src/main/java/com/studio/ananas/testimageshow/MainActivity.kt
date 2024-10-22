package com.studio.ananas.testimageshow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.studio.ananas.testimageshow.api.data.ScreenResponse
import com.studio.ananas.testimageshow.ui.components.Slideshow
import com.studio.ananas.testimageshow.ui.theme.TestImageShowTheme
import com.studio.ananas.testimageshow.ui.vms.ApiViewModel
import java.io.File

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(apiViewModel: ApiViewModel = viewModel()) {
    val screenResponse by apiViewModel.apiResponse
    val isLoading by apiViewModel.isLoading
    val context = LocalContext.current

    // Define the output directory
    val outputDir = File(context.filesDir, "downloads")
    if (!outputDir.exists()) {
        outputDir.mkdir()
    }

    // UI components
    Scaffold(
        topBar = { TopAppBar(title = { Text("Signage App Test") }) }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                screenResponse != null -> {
                    apiViewModel.downloadAllFiles(screenResponse!!, outputDir)
                    Slideshow(mediaFiles = screenResponse!!.playlists.flatMap { it.playlistItems })
                }
                /*ScreenResponseContent(
                    screenResponse = screenResponse!!,
                    viewModel = apiViewModel,
                    outputDirectory = outputDir
                )*/
                else -> {
                    Button(
                        onClick = { apiViewModel.fetchData() },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text("Fetch Data")
                    }
                }
            }
        }
    }
}

//@Composable
//fun ScreenResponseContent(screenResponse: ScreenResponse) {
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        // Display general screen information
//        Text(text = "Screen Key: ${screenResponse.screenKey}")
//        Text(text = "Company: ${screenResponse.company}")
//        Text(text = "Breakpoint Interval: ${screenResponse.breakpointInterval}")
//
//        // Display modified timestamp
//        Text(text = "Last Modified: ${screenResponse.modified}")
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Iterate over playlists
//        Text(text = "Playlists:")
//        screenResponse.playlists.forEach { playlist ->
//            Text(text = "Playlist Key: ${playlist.playlistKey}")
//            Text(text = "Channel Time: ${playlist.channelTime}")
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Iterate over playlist items
//            playlist.playlistItems.forEachIndexed { index, item ->
//                Text(text = "  Item ${index + 1}:")
//                Text(text = "    Creative Label: ${item.creativeLabel}")
//                Text(text = "    Creative Key: ${item.creativeKey}")
//                Text(text = "    Duration: ${item.duration} seconds")
//                Text(text = "    Start Date: ${item.startDate}")
//                Text(text = "    Expire Date: ${item.expireDate}")
//                Text(text = "    Slide Priority: ${item.slidePriority}")
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//        }
//    }
//}

//@Composable
//fun ScreenResponseContent(
//    screenResponse: ScreenResponse,
//    viewModel: ApiViewModel,
//    outputDirectory: File
//) {
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        // Display general screen information
//        Text(text = "Screen Key: ${screenResponse.screenKey}")
////        Text(text = "Company: ${screenResponse.company}")
////        Text(text = "Breakpoint Interval: ${screenResponse.breakpointInterval}")
//
////        // Display modified timestamp
////        Text(text = "Last Modified: ${screenResponse.modified}")
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Iterate over playlists
//        Text(text = "Playlists:")
//        var hasPlaylistItems = false
//        screenResponse.playlists.forEach { playlist ->
//            Text(text = "Playlist Key: ${playlist.playlistKey}")
////            Text(text = "Channel Time: ${playlist.channelTime}")
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Check if there are any playlist items
//            if (playlist.playlistItems.isNotEmpty()) {
//                hasPlaylistItems = true
//                // Iterate over playlist items
//                playlist.playlistItems.forEachIndexed { index, item ->
////                    Text(text = "  Item ${index + 1}:")
////                    Text(text = "    Creative Label: ${item.creativeLabel}")
//                    Text(text = "    Creative Key: ${item.creativeKey}")
////                    Text(text = "    Duration: ${item.duration} seconds")
////                    Text(text = "    Start Date: ${item.startDate}")
////                    Text(text = "    Expire Date: ${item.expireDate}")
////                    Text(text = "    Slide Priority: ${item.slidePriority}")
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Show the download button only if there are playlist items
//        if (hasPlaylistItems) {
//            Button(
//                onClick = {
//                    viewModel.downloadAllFiles(screenResponse, outputDirectory)
//                }
//            ) {
//                Text("Download All Files")
//            }
//        } else {
//            Text(text = "No files available for download")
//        }
//    }
//}
//
