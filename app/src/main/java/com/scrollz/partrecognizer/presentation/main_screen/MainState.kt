package com.scrollz.partrecognizer.presentation.main_screen

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap

@Stable
data class MainState(
    val isPermissionDialogVisible: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val isSelecting: Boolean = false,
    val selectedReportsIds: SnapshotStateMap<Long, Boolean> = mutableStateMapOf()
)
