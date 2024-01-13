package com.scrollz.partrecognizer.presentation.scanner.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ScannerScaffold(
    modifier: Modifier = Modifier,
    isSnackbarVisible: Boolean,
    isDetailDialogVisible: Boolean,
    isSaveButtonEnabled: Boolean,
    isSaved: Boolean,
    detectedDetails: ImmutableList<String>,
    resultImage: String?,
    openImage: String?,
    openDetailDialog: () -> Unit,
    viewImage: (String?) -> Unit,
    closeImage: () -> Unit,
    save: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isDetailDialogVisible,
            enter = fadeIn(tween(400)),
            exit = fadeOut(tween(400)),
            label = "detail_dialog"
        ) {
            DetailSheet(
                isSaveButtonEnabled = isSaveButtonEnabled,
                isSaved = isSaved,
                detectedDetails = detectedDetails,
                resultImage = resultImage,
                openImage = openImage,
                viewImage = viewImage,
                closeImage = closeImage,
                save = save
            )
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = isSnackbarVisible && detectedDetails.isNotEmpty(),
            enter = fadeIn(tween(400)),
            exit = fadeOut(tween(400)),
            label = "snackbar"
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(enabled = detectedDetails.isNotEmpty()) { openDetailDialog() },
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(1.0f, fill = true),
                        text = detectedDetails.joinToString(separator = ", "),
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
