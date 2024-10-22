package com.studio.ananas.testimageshow.ui.components

import android.net.Uri
import android.view.ViewGroup
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

/**
 * A Composable to cycle through a list of media objects
 */
@Composable
fun Slideshow(mediaFiles: List<PlaylistItem>) {
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(mediaFiles) {
        if (mediaFiles.isNotEmpty()) {
            while (true) {
                currentIndex = (currentIndex + 1) % mediaFiles.size

                // Get the duration from playlist item
                val duration: Long = (mediaFiles[currentIndex].duration*1000).toLong()
                delay(duration)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (mediaFiles.isNotEmpty()) {
            val currentMedia = mediaFiles[currentIndex]
            currentMedia.localFilePath?.let { localFilePath ->
                // TODO make the distinction by media type, not extension
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

/**
 * A Composable to show a video file. Uses a deprecated lib.
 * Basically, a POC to save time.
 * TODO Find an up-to-date solution
 */
@Composable
fun VideoPlayer(videoUrl: String) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    DisposableEffect(Unit) {
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

        onDispose {
            exoPlayer.release() // Release the player when the composable is disposed
        }
    }

    // Needs to be wrapped in a good old View
    AndroidView(factory = {
        PlayerView(context).apply {
            player = exoPlayer
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT // Make sure the player view is fully visible
            )
        }
    }, update = {
        it.player = exoPlayer
    })
}

