package com.scrollz.partrecognizer.presentation.scanner

import androidx.camera.core.ImageProxy

sealed class ScannerEvent {
    data object ToggleTorch: ScannerEvent()
    data object ToggleCloseDialog: ScannerEvent()
    data object OpenDetailDialog: ScannerEvent()
    data object CloseDetailDialog: ScannerEvent()
    data object CloseImage: ScannerEvent()
    data object Save: ScannerEvent()
    data class ViewImage(val data: String?): ScannerEvent()
    data class AnalyzeImage(val imageProxy: ImageProxy): ScannerEvent()
}
