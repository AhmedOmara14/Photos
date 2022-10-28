package com.photos.presentation.all_photos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photos.common.Constants
import com.photos.common.Resource
import com.photos.domain.model.all_photos.AllPhotos
import javax.inject.Inject
import com.photos.domain.use_case.get_all_photos.GetAllPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class AllPhotosViewModel @Inject constructor(val getAllPhotosUseCase: GetAllPhotosUseCase) :
    ViewModel() {
    private val _state = mutableStateOf(AllPhotosState())
    val AllPhotosState: State<AllPhotosState> = _state
    val page = mutableStateOf(1)
    var photosListScrollPosition = 0
    private var result: Resource<List<AllPhotos>>? = null

    init {
        getAllPhotos(page.value, Constants.PAGE_SIZE)
    }

    private fun getAllPhotos(
        page: Int,
        perPage: Int
    ) {
        getAllPhotosUseCase(page, perPage).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    _state.value = AllPhotosState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = AllPhotosState(
                        allPhotos = response.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value =
                        AllPhotosState(error = response.message ?: "unexpected error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendPhoto(photos: List<AllPhotos>) {
        val current = AllPhotosState.value.allPhotos?.let { ArrayList(it) }
        current?.addAll(photos)
        _state.value = AllPhotosState(
            allPhotos = current,
            isLoading = false
        )
    }

    fun nextPage() {
        viewModelScope.launch {
            if ((photosListScrollPosition + 1) >= (page.value * Constants.PAGE_SIZE)) {
                incrementPage()
                if (page.value > 1) {
                    getAllPhotosUseCase(
                        page.value,
                        Constants.PAGE_SIZE
                    ).collect {
                        result = it
                    }
                    result?.data?.let { appendPhoto(it) }
                }

            }
        }
    }

    private fun incrementPage() {
        page.value += 1
    }

    fun onChangePhotosListScrollPosition(position: Int) {
        photosListScrollPosition = position
    }
}