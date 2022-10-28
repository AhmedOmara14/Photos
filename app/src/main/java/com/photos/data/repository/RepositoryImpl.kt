package com.photos.data.repository


import com.photos.data.remote.Api
import com.photos.domain.model.all_photos.AllPhotos
import com.photos.domain.repository.Repository
import javax.inject.Inject
import retrofit2.Response


class RepositoryImpl @Inject constructor(val api: Api) : Repository {
    override suspend fun getMovies(page: Int,perPage:Int): Response<List<AllPhotos>?> {
        return api.getPhotos(page, perPage)
    }
}