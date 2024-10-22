package com.studio.ananas.testimageshow.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.studio.ananas.testimageshow.api.data.PlaylistItem
import kotlinx.coroutines.delay
import java.io.File

@Composable
fun Slideshow(mediaFiles: List<PlaylistItem>) {
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(mediaFiles) {
        if (mediaFiles.isNotEmpty()) {
            while (true) {
//                delay(5000) // Delay for 5 seconds
                currentIndex = (currentIndex + 1) % mediaFiles.size // Cycle through the media files
                val duration: Long = (mediaFiles[currentIndex].duration*1000).toLong()
                delay(duration)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (mediaFiles.isNotEmpty()) {
            val currentMedia = mediaFiles[currentIndex]
            currentMedia.localFilePath?.let { localFilePath ->
                if (localFilePath.endsWith(".jpg") || localFilePath.endsWith(".png")) {
                    // Display image
                    val painter = rememberImagePainter(File(localFilePath))
                    Image(painter = painter, contentDescription = null, modifier = Modifier.fillMaxSize())
                } else if (localFilePath.endsWith(".mp4")) {
                    // Display video
                    VideoPlayer(videoUrl = Uri.fromFile(File(localFilePath)).toString())
                }
            }
        }
    }
}

@Composable
fun VideoPlayer(videoUrl: String) {
    val context = LocalContext.current
    // Remember the ExoPlayer instance
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    // Set up the media item and prepare the player
    DisposableEffect(Unit) {
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

        onDispose {
            exoPlayer.release() // Release the player when the composable is disposed
        }
    }

    // Embed PlayerView in Compose using AndroidView
    AndroidView(factory = {
        PlayerView(context).apply {
            player = exoPlayer // Set the ExoPlayer instance to the PlayerView
        }
    }, update = {
        it.player = exoPlayer // Ensure PlayerView is updated with the ExoPlayer instance
    })
}
