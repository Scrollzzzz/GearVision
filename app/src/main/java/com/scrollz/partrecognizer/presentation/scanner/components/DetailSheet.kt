package com.scrollz.partrecognizer.presentation.scanner.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import com.scrollz.partrecognizer.R
import com.scrollz.partrecognizer.presentation.common.ImageView
import com.scrollz.partrecognizer.presentation.common.ReferenceImagesRow
import kotlinx.collections.immutable.ImmutableList

@Composable
fun DetailSheet(
    modifier: Modifier = Modifier,
    isSaveButtonEnabled: Boolean,
    isSaved: Boolean,
    detectedDetails: ImmutableList<String>,
    resultImage: String?,
    openImage: String?,
    viewImage: (String?) -> Unit,
    closeImage: () -> Unit,
    save: () -> Unit
) {
    val scrollState = rememberScrollState()

    Crossfade(
        modifier = Modifier.zIndex(1f),
        targetState = openImage,
        animationSpec = tween(400),
        label = "image_view"
    ) { image ->
        if (image != null) {
            ImageView(
                image = image,
                closeImage = closeImage
            )
        }
    }

    SubcomposeAsyncImage(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                renderEffect = BlurEffect(100f, 100f, TileMode.Repeated)
            },
        model = resultImage,
        contentScale = ContentScale.Crop,
        contentDescription = null,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.scrim, BlendMode.SrcOver),
        loading = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {}
        },
        error = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {}
        },
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .windowInsetsPadding(WindowInsets.displayCutout)
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 64.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            shape = RoundedCornerShape(32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                for (detail in detectedDetails) {
                    Text(
                        text = detail,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }

        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4 / 3f)
                .clip(RoundedCornerShape(32.dp))
                .clickable { viewImage(resultImage) },
            model = resultImage,
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            loading = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4 / 3f),
                    shape = RoundedCornerShape(32.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {}
            },
            error = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4 / 3f),
                    shape = RoundedCornerShape(32.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {}
            }
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            shape = RoundedCornerShape(32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (detectedDetails.size == 1) {
                    Text(
                        text = stringResource(R.string.detail_references),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    ReferenceImagesRow(
                        referenceName = detectedDetails.first(),
                        viewImage = viewImage
                    )
                } else {
                    Text(
                        text = stringResource(R.string.detail_many),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    for (detectedDetail in detectedDetails) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = detectedDetail,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        ReferenceImagesRow(
                            referenceName = detectedDetail,
                            viewImage = viewImage
                        )
                    }
                }
            }
        }

        Crossfade(
            targetState = isSaved,
            animationSpec = tween(200),
            label = "save_button_content"
        ) { isSaved ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))
                    .clickable(enabled = isSaveButtonEnabled) { save() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (isSaved) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.button_saved),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = stringResource(R.string.button_save),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars))
    }
}
