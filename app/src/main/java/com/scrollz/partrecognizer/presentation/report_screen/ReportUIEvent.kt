package com.scrollz.partrecognizer.presentation.report_screen

import androidx.compose.runtime.Immutable

@Immutable
sealed class ReportUIEvent {
    data object NavigateBack: ReportUIEvent()
}
