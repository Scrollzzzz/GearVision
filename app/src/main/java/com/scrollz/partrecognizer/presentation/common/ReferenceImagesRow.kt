package com.scrollz.partrecognizer.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.scrollz.partrecognizer.utils.getImageURL

@Composable
fun ReferenceImagesRow(
    modifier: Modifier = Modifier,
    referenceName: String,
    viewImage: (String) -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current.density
    var size by remember { mutableStateOf(0.dp) }

    Row(
        modifier = Modifier.onGloballyPositioned { coordinates ->
            size = ((coordinates.size.width / density).dp - 8.dp) / 3
        },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (i in 1..3) {
            val url = remember { "$referenceName-$i".getImageURL() }
            SubcomposeAsyncImage(
                modifier = modifier
                    .size(size)
                    .weight(1.0f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        viewImage(url)
                    },
                model = ImageRequest.Builder(context)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}
