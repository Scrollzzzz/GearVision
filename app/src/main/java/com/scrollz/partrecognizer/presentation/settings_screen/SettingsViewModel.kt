package com.scrollz.partrecognizer.presentation.settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrollz.partrecognizer.R
import com.scrollz.partrecognizer.domain.model.Theme
import com.scrollz.partrecognizer.domain.use_cases.GetSettingsUseCase
import com.scrollz.partrecognizer.domain.use_cases.SaveBaseUrlUseCase
import com.scrollz.partrecognizer.domain.use_cases.SwitchThemeUseCase
import com.scrollz.partrecognizer.utils.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val switchThemeUseCase: SwitchThemeUseCase,
    private val saveBaseUrlUseCase: SaveBaseUrlUseCase
): ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init { getSettings() }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SaveBaseUrl -> saveBaseUrl()
            is SettingsEvent.SaveBaseUrlQR -> saveBaseUrlQR(event.qr)
            is SettingsEvent.SwitchTheme -> switchTheme(event.theme)
            is SettingsEvent.ChangeIP -> _state.update { it.copy(domain = event.value) }
            is SettingsEvent.ChangePort -> _state.update { it.copy(port = event.value) }
            is SettingsEvent.ConsumeUIEvent -> _state.update { it.copy(uiEvent = null) }
            is SettingsEvent.TogglePermissionDialog -> _state.update {
                it.copy(isPermissionDialogVisible = !it.isPermissionDialogVisible)
            }
        }
    }

    private fun getSettings() {
        getSettingsUseCase().onEach { settings ->
            _state.update { state ->
                state.copy(
                    theme = settings.theme,
                    domain = settings.baseUrl.domain,
                    port = settings.baseUrl.port
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun saveBaseUrl() {
        viewModelScope.launch {
            val result = saveBaseUrlUseCase(_state.value.domain, _state.value.port)
            showSnackbar(result)
        }
    }

    private fun saveBaseUrlQR(qr: String) {
        viewModelScope.launch {
            val url = qr.trim().split(':')
            val domain = url.getOrElse(0) { "" }
            val port = url.getOrElse(1) { "" }
            val result = saveBaseUrlUseCase(domain, port)
            showSnackbar(result)
        }
    }

    private fun <T> showSnackbar(result: Result<T>) {
        _state.update { state ->
            state.copy(
                uiEvent = SettingsUIEvent.ShowSnackbar(
                    message = UIText.StringResource(
                        if (result.isSuccess) R.string.snackbar_changes_saved
                        else R.string.snackbar_err_save
                    )
                )
            )
        }
    }

    private fun switchTheme(theme: Theme) = viewModelScope.launch { switchThemeUseCase(theme) }

}
