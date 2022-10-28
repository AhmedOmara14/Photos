package com.photos.presentation.all_photos.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.photos.domain.model.all_photos.AllPhotos

@Composable
fun Item_photo(
    photos: AllPhotos,
    onExecutePhoto: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            backgroundColor = Color.White, modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.LightGray)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(photos.thumbnailUrl),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(150.dp)
                            .clickable { onExecutePhoto(photos.thumbnailUrl) }
                    )
                }
            }
        }
        Text(
            text = photos.title,
            textAlign = TextAlign.Start,
            style = TextStyle(
                fontSize = 13.sp,
                color = Color.Black
            )
        )
    }

}