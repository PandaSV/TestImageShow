package com.studio.ananas.testimageshow.api

data class Playlist(
    val channelTime: Int,
    val playlistItems: List<PlaylistItem>,
    val playlistKey: String
)