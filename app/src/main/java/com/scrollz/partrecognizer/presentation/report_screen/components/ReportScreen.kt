package com.scrollz.partrecognizer.presentation.report_screen.components

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.scrollz.partrecognizer.R
import com.scrollz.partrecognizer.presentation.common.ImageView
import com.scrollz.partrecognizer.presentation.common.ReferenceImagesRow
import com.scrollz.partrecognizer.presentation.report_screen.ReportEvent
import com.scrollz.partrecognizer.presentation.report_screen.ReportState
import com.scrollz.partrecognizer.presentation.report_screen.ReportUIEvent
import kotlinx.collections.immutable.ImmutableList

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ReportScreen(
    modifier: Modifier = Modifier,
    state: ReportState,
    onEvent: (ReportEvent) -> Unit,
    navigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val scrollOffset by remember { derivedStateOf { scrollState.value } }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight by remember { derivedStateOf { min(screenHeight, screenWidth) } }
    val imageHeightPx = with(LocalDensity.current) { imageHeight.toPx() }
    val maxOffset by remember { derivedStateOf { imageHeightPx / 1.6f } }

    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(state.uiEvent) {
        state.uiEvent?.let { uiEvent ->
            when (uiEvent) {
                is ReportUIEvent.NavigateBack -> navigateBack()
            }
            onEvent(ReportEvent.ConsumeUIEvent)
        }
    }

    if (state.isDeleteDialogVisible) {
        DeleteDialog(
            onDismiss = { onEvent(ReportEvent.ToggleDeleteDialog) },
            onConfirm = { onEvent(ReportEvent.DeleteReport) }
        )
    }

    Crossfade(
        modifier = Modifier.zIndex(1f),
        targetState = state.openImage,
        animationSpec = tween(400),
        label = "image_view"
    ) { image ->
        if (image != null) {
            ImageView(
                image = image,
                closeImage = { onEvent(ReportEvent.CloseImage) }
            )
        }
    }
    
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                isScrolled = scrollOffset >= maxOffset,
                navigateBack = navigateBack,
                deleteReport = { onEvent(ReportEvent.ToggleDeleteDialog) }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.resultImage)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.surface.copy(
                        alpha = if (scrollOffset >= maxOffset) 1f else scrollOffset / maxOffset
                    ),
                    blendMode = BlendMode.SrcOver
                ),
                error = {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {}
                },
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight - 32.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { onEvent(ReportEvent.OpenImage(state.resultImage)) }
                )
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Names(
                            modifier = Modifier.fillMaxWidth(),
                            screenWidth = screenWidth.value,
                            detectedDetailsNames = state.detectedDetailsNames
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(24.dp))
                        DateTime(
                            modifier = Modifier.fillMaxWidth(),
                            dateTime = state.dateTime
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(24.dp))
                        MockCharacteristics()
                        Spacer(modifier = Modifier.height(24.dp))
                        Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(24.dp))
                        Compare(
                            detectedDetails = state.detectedDetails,
                            viewImage = { image -> onEvent(ReportEvent.OpenImage(image)) }
                        )
                        Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars))
                    }
                }
            }
        }
    }
}


@Composable
fun Names(
    modifier: Modifier = Modifier,
    screenWidth: Float,
    detectedDetailsNames: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        SlidingText(
            durationMillis = (detectedDetailsNames.length * 250 - screenWidth * 10).toInt()
                .coerceAtLeast(1000)
        ) {
            Text(
                text = detectedDetailsNames,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun DateTime(
    modifier: Modifier = Modifier,
    dateTime: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.CalendarMonth,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = dateTime,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun MockCharacteristics(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Article,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Характеристики",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = "Наименование",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSecondary
    )
    Text(
        text = "Кронштейн",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "Вес",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSecondary
    )
    Text(
        text = "720 г",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "Размеры Д×Ш×В",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSecondary
    )
    Text(
        text = "120x65x45",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun Compare(
    modifier: Modifier = Modifier,
    detectedDetails: ImmutableList<String>,
    viewImage: (String?) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Compare,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = if (detectedDetails.size == 1) stringResource(R.string.detail_references)
            else stringResource(R.string.detail_many),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
    if (detectedDetails.size == 1) {
        Spacer(modifier = Modifier.height(32.dp))
        ReferenceImagesRow(
            referenceName = detectedDetails.first(),
            viewImage = viewImage
        )
    } else {
        for (detectedDetail in detectedDetails) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = detectedDetail,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            ReferenceImagesRow(
                referenceName = detectedDetail,
                viewImage = viewImage
            )
        }
    }
}
