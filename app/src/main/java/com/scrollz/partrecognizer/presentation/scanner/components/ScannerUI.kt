package com.scrollz.partrecognizer.presentation.scanner.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.scrollz.partrecognizer.R
import com.scrollz.partrecognizer.utils.UIText

@Composable
fun ScannerUI(
    modifier: Modifier = Modifier,
    isTorchEnabled: Boolean,
    isCloseDialogVisible: Boolean,
    error: UIText?,
    toggleTorch: () -> Unit,
    toggleCloseDialog: () -> Unit,
    closeScanner: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    if (isCloseDialogVisible) {
        CloseDialog(
            onDismiss = toggleCloseDialog,
            onConfirm = closeScanner
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clickable(
                        onClick = toggleCloseDialog,
                        interactionSource = interactionSource,
                        indication = null
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.button_close),
                    tint = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clickable(
                        onClick = toggleTorch,
                        interactionSource = interactionSource,
                        indication = null
                    ),
                contentAlignment = Alignment.Center
            ) {
                Crossfade(
                    targetState = isTorchEnabled,
                    animationSpec = tween(400),
                    label = "torch"
                ) { isTorchEnabled ->
                    Icon(
                        imageVector = if (isTorchEnabled) Icons.Default.FlashOn else Icons.Default.FlashOff,
                        contentDescription = stringResource(R.string.button_flash),
                        tint = Color.White
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = error != null,
            enter = fadeIn(animationSpec = tween(400)),
            exit = fadeOut(animationSpec = tween(400)),
            label = "error row"
        ) {
            if (error != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.WarningAmber,
                        contentDescription = stringResource(R.string.error),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = error.asString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
