package com.scrollz.partrecognizer.presentation.settings_screen

import com.scrollz.partrecognizer.domain.model.Theme

sealed class SettingsEvent {
    data object ConsumeUIEvent: SettingsEvent()
    data class ChangeIP(val value: String): SettingsEvent()
    data class ChangePort(val value: String): SettingsEvent()
    data class SwitchTheme(val theme: Theme): SettingsEvent()
    data object TogglePermissionDialog: SettingsEvent()
    data class SaveBaseUrlQR(val qr: String): SettingsEvent()
    data object SaveBaseUrl: SettingsEvent()
}
