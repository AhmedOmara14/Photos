package com.photos.presentation.all_photos

import com.photos.domain.model.all_photos.AllPhotos

data class AllPhotosState(
    val isLoading: Boolean = false,
    var allPhotos: List<AllPhotos>? = null,
    val error: String = ""
)