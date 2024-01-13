package com.scrollz.partrecognizer.presentation.settings_screen

import androidx.compose.runtime.Immutable
import com.scrollz.partrecognizer.domain.model.Theme

@Immutable
data class SettingsState(
    val uiEvent: SettingsUIEvent? = null,
    val isButtonsEnabled: Boolean = true,
    val isPermissionDialogVisible: Boolean = false,
    val theme: Theme = Theme.System,
    val domain: String = "",
    val port: String = ""
)
