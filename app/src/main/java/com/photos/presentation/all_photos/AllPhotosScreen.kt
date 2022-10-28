package com.photos.presentation.all_photos

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.photos.common.Constants
import com.photos.presentation.all_photos.component.Item_photo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllPhotosScreen(
    viewModel: AllPhotosViewModel = hiltViewModel(),
) {
    val allPhotosState = viewModel.AllPhotosState.value
    val context = LocalContext.current
    val page = viewModel.page.value
    val showDialog = remember { mutableStateOf(false) }
    val photoUrl = remember { mutableStateOf("") }

    if (showDialog.value) {
        PhotoScreen(setShowDialog = {
            showDialog.value = false
        }, photoUrl.value)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if (allPhotosState.isLoading)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        else {
            if (allPhotosState.allPhotos == null) {
                Text(
                    "Not Founded Photo",
                    maxLines = 1,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 20.dp),
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace
                    )
                )
            } else {
                allPhotosState.allPhotos.let {
                    it?.let { it1 ->
                        LazyVerticalGrid(
                            cells = GridCells.Fixed(2),
                            modifier = Modifier.padding(20.dp)
                        ) {
                            itemsIndexed(it1) { index, photo ->
                                viewModel.onChangePhotosListScrollPosition(index)
                                if ((index + 1) >= (page * Constants.PAGE_SIZE) && !allPhotosState.isLoading) {
                                    viewModel.nextPage()
                                }
                                Item_photo(
                                    photos = photo,
                                    onExecutePhoto = {
                                        showDialog.value = true
                                        photoUrl.value = photo.thumbnailUrl
                                    }
                                )
                            }
                        }

                    }
                }
            }
        }
    }
    if (allPhotosState.error.isNotBlank()) {
        Toast.makeText(context, allPhotosState.error, Toast.LENGTH_SHORT).show()
    }
}
