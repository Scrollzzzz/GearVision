package com.scrollz.partrecognizer.presentation.settings_screen

import androidx.compose.runtime.Immutable
import com.scrollz.partrecognizer.utils.UIText

@Immutable
sealed class SettingsUIEvent {
    data class ShowSnackbar(val message: UIText): SettingsUIEvent()
}
