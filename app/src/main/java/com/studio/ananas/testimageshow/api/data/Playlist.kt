package com.studio.ananas.testimageshow.api.data

/**
 * A data class inferred from the test api response structure,
 * Representing a list of media objects to display
 */
data class Playlist(
    val channelTime: Int,
    val playlistItems: List<PlaylistItem>,
    val playlistKey: String
)