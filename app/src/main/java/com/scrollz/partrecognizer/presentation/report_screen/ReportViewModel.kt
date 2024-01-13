package com.scrollz.partrecognizer.presentation.report_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrollz.partrecognizer.domain.use_cases.DeleteReportUseCase
import com.scrollz.partrecognizer.domain.use_cases.GetReportUseCase
import com.scrollz.partrecognizer.utils.toFineDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getReportUseCase: GetReportUseCase,
    private val deleteReportUseCase: DeleteReportUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(ReportState())
    val state = _state.asStateFlow()

    private var id = -1L

    init {
        id = savedStateHandle.get<Long>("reportID") ?: -1
        getReport()
    }

    fun onEvent(event: ReportEvent) {
        when (event) {
            is ReportEvent.DeleteReport -> deleteReport()
            is ReportEvent.ConsumeUIEvent -> _state.update { it.copy(uiEvent = null) }
            is ReportEvent.CloseImage -> _state.update { it.copy(openImage = null) }
            is ReportEvent.OpenImage -> _state.update { it.copy(openImage = event.data) }
            is ReportEvent.ToggleDeleteDialog -> _state.update {
                it.copy(isDeleteDialogVisible = !it.isDeleteDialogVisible)
            }
        }
    }

    private fun getReport() {
        viewModelScope.launch {
            getReportUseCase(id)
                .onSuccess { report ->
                    val detectedDetails = report.detectedDetails.split(", ").toImmutableList()
                    _state.update { state ->
                        state.copy(
                            detectedDetails = detectedDetails,
                            detectedDetailsNames = detectedDetails.joinToString(),
                            dateTime = report.dateTime.toFineDateTime(),
                            resultImage = report.resultImagePath
                        )
                    }
                }
                .onFailure { _state.update { it.copy(screenError = true) } }
        }
    }

    private fun deleteReport() {
        viewModelScope.launch { deleteReportUseCase(id, _state.value.resultImage) }
        _state.update {
            it.copy(
                isDeleteDialogVisible = false,
                uiEvent = ReportUIEvent.NavigateBack
            )
        }
    }

}
