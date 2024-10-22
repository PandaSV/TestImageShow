package com.studio.ananas.testimageshow.api.data

/**
 * A data class inferred from the test api response structure,
 * Representing a list of playlists
 */
data class ScreenResponse(
    val screenKey: String,
    val company: String,
    val breakpointInterval: Int,
    val playlists: List<Playlist>,
    val modified: Long
)
