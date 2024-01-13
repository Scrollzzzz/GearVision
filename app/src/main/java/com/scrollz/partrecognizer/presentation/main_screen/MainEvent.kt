package com.scrollz.partrecognizer.presentation.main_screen

sealed class MainEvent {
    data class SelectReport(val id: Long): MainEvent()
    data object CancelSelecting : MainEvent()
    data object TogglePermissionDialog: MainEvent()
    data object ToggleDeleteDialog: MainEvent()
    data object DeleteReports: MainEvent()
}
