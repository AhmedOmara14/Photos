package com.photos.domain.use_case.get_all_photos

import com.photos.common.Resource
import com.photos.domain.model.all_photos.AllPhotos
import com.photos.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllPhotosUseCase @Inject constructor(val repository: Repository) {
    operator fun invoke(
        page: Int,
        perPage:Int
    ): Flow<Resource<List<AllPhotos>>> = flow {
        try {
            emit(Resource.Loading<List<AllPhotos>>())
            val photos = repository.getPhotos(page,perPage)
            emit(Resource.Success<List<AllPhotos>>(photos.body()))
        } catch (e: HttpException) {
            emit(Resource.Error<List<AllPhotos>>(e.localizedMessage ?: "an Error Occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<AllPhotos>>("No Internet Connection, Check your Internet"))
        }
    }
}