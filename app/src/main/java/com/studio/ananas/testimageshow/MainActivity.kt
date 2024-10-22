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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.studio.ananas.testimageshow.api.data.ScreenResponse
import com.studio.ananas.testimageshow.ui.theme.TestImageShowTheme
import com.studio.ananas.testimageshow.ui.vms.ApiViewModel

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
    val apiResponse by apiViewModel.apiResponse
    val isLoading by apiViewModel.isLoading

    // UI components
    Scaffold(
        topBar = { TopAppBar(title = { Text("API Call Example") }) }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                apiResponse != null -> ScreenResponseContent(apiResponse!!)
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

@Composable
fun ScreenResponseContent(screenResponse: ScreenResponse) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Display general screen information
        Text(text = "Screen Key: ${screenResponse.screenKey}")
        Text(text = "Company: ${screenResponse.company}")
        Text(text = "Breakpoint Interval: ${screenResponse.breakpointInterval}")

        // Display modified timestamp
        Text(text = "Last Modified: ${screenResponse.modified}")

        Spacer(modifier = Modifier.height(16.dp))

        // Iterate over playlists
        Text(text = "Playlists:")
        screenResponse.playlists.forEach { playlist ->
            Text(text = "Playlist Key: ${playlist.playlistKey}")
            Text(text = "Channel Time: ${playlist.channelTime}")
            Spacer(modifier = Modifier.height(8.dp))

            // Iterate over playlist items
            playlist.playlistItems.forEachIndexed { index, item ->
                Text(text = "  Item ${index + 1}:")
                Text(text = "    Creative Label: ${item.creativeLabel}")
                Text(text = "    Creative Key: ${item.creativeKey}")
                Text(text = "    Duration: ${item.duration} seconds")
                Text(text = "    Start Date: ${item.startDate}")
                Text(text = "    Expire Date: ${item.expireDate}")
                Text(text = "    Slide Priority: ${item.slidePriority}")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
