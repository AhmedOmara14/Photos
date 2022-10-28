package com.photos.presentation.all_photos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter


@Composable
fun PhotoScreen(setShowDialog: (Boolean) -> Unit, photoUrl: String) {
    val scale = remember { mutableStateOf(1f) }
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            backgroundColor = Color.White
        ) {
            Box(
                modifier = Modifier.size(500.dp)
                    .clip(RectangleShape)
                    .background(Color.White)
                    .pointerInput(Unit) {
                        detectTransformGestures { _, _, zoom, _ ->
                            scale.value *= zoom
                        }
                    }
            ) {
                Image(
                    modifier = Modifier
                        .size(500.dp)
                        .align(Alignment.Center)
                        .graphicsLayer(
                            scaleX = maxOf(.5f, minOf(3f, scale.value)),
                            scaleY = maxOf(.5f, minOf(3f, scale.value)),
                        ),
                    contentDescription = null,
                    painter = rememberAsyncImagePainter(photoUrl),
                )
            }
        }
    }
}
