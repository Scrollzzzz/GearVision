package com.scrollz.partrecognizer.presentation.report_screen

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ReportState(
    val isDeleteDialogVisible: Boolean = false,
    val uiEvent: ReportUIEvent? = null,
    val detectedDetails: ImmutableList<String> = persistentListOf(),
    val detectedDetailsNames: String = "",
    val dateTime: String = "",
    val resultImage: String? = null,
    val openImage: String? = null,
    val screenError: Boolean = false
)
