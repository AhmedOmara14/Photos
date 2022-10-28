package com.photos.domain.model.all_photos

data class AllPhotos(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)