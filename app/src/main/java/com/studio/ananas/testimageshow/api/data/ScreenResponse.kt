package com.studio.ananas.testimageshow.api.data

data class ScreenResponse(
    val screenKey: String,
    val company: String,
    val breakpointInterval: Int,
    val playlists: List<Playlist>,
    val modified: Long
)
