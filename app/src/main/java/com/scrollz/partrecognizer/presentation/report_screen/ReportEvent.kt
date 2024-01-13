package com.scrollz.partrecognizer.presentation.report_screen

sealed class ReportEvent {
    data object ConsumeUIEvent: ReportEvent()
    data object ToggleDeleteDialog: ReportEvent()
    data object DeleteReport: ReportEvent()
    data object CloseImage: ReportEvent()
    data class OpenImage(val data: String?): ReportEvent()
}
