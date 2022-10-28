package com.photos.domain.repository

import com.photos.domain.model.all_photos.AllPhotos
import retrofit2.Response


interface Repository {
    suspend fun getMovies(
       page: Int,perPage:Int
    ): Response<List<AllPhotos>?>

}