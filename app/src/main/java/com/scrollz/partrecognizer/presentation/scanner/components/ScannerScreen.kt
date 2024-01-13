package com.scrollz.partrecognizer.presentation.scanner.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scrollz.partrecognizer.presentation.scanner.ScannerEvent
import com.scrollz.partrecognizer.presentation.scanner.ScannerState

@Composable
fun ScannerScreen(
    modifier: Modifier = Modifier,
    state: ScannerState,
    onEvent: (ScannerEvent) -> Unit,
    closeScanner: () -> Unit
) {
    BackHandler {
        if (state.isDetailDialogVisible) {
            onEvent(ScannerEvent.CloseDetailDialog)
        } else {
            onEvent(ScannerEvent.ToggleCloseDialog)
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            isTorchEnabled = state.isTorchEnabled,
            analyzeImage = { imageProxy -> onEvent(ScannerEvent.AnalyzeImage(imageProxy)) },
            closeScanner = closeScanner
        )
        ScannerUI(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .windowInsetsPadding(WindowInsets.statusBars)
                .windowInsetsPadding(WindowInsets.displayCutout)
                .windowInsetsPadding(WindowInsets.navigationBars),
            isTorchEnabled = state.isTorchEnabled,
            isCloseDialogVisible = state.isCloseDialogVisible,
            error = state.error,
            toggleTorch = { onEvent(ScannerEvent.ToggleTorch) },
            toggleCloseDialog = { onEvent(ScannerEvent.ToggleCloseDialog) },
            closeScanner = closeScanner
        )
        ScannerScaffold(
            modifier = Modifier.fillMaxSize(),
            isSnackbarVisible = state.isSnackbarVisible,
            isDetailDialogVisible = state.isDetailDialogVisible,
            isSaveButtonEnabled = state.isSaveButtonEnabled,
            isSaved = state.isSaved,
            detectedDetails = state.detectedDetails,
            resultImage = state.resultImage,
            openImage = state.openImage,
            openDetailDialog = { onEvent(ScannerEvent.OpenDetailDialog) },
            viewImage = { image -> onEvent(ScannerEvent.ViewImage(image)) },
            closeImage = { onEvent(ScannerEvent.CloseImage) },
            save = { onEvent(ScannerEvent.Save) }
        )
    }
}
