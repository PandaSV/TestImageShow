package com.studio.ananas.testimageshow.api.data

data class PlaylistItem(
    val creativeRefKey: String?,
    val duration: Int,
    val expireDate: String,
    val startDate: String,
    val collectStatistics: Boolean,
    val creativeLabel: String,
    val slidePriority: Int,
    val playlistKey: String,
    val creativeKey: String,
    val orderKey: Int,
    val eventTypesList: List<String>,
    val localFilePath: String? = null
)