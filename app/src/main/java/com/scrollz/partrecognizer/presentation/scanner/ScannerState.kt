package com.scrollz.partrecognizer.presentation.scanner

import androidx.compose.runtime.Immutable
import com.scrollz.partrecognizer.utils.UIText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ScannerState(
    val isTorchEnabled: Boolean = false,
    val isSnackbarVisible: Boolean = true,
    val isCloseDialogVisible: Boolean = false,
    val isDetailDialogVisible: Boolean = false,
    val isSaveButtonEnabled: Boolean = true,
    val isSaved: Boolean = false,

    val detectedDetails: ImmutableList<String> = persistentListOf(),
    val resultImage: String? = null,
    val openImage: String? = null,
    val error: UIText? = null
) {
    val isDialogVisible get() = isDetailDialogVisible || isCloseDialogVisible
}
