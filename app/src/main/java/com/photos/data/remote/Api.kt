package com.photos.data.remote

import com.photos.domain.model.all_photos.AllPhotos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {
    @GET("photos")
    suspend fun getPhotos(
        @Query("_start") page: Int,
        @Query("_limit") perPage: Int,
    ): Response<List<AllPhotos>?>
}