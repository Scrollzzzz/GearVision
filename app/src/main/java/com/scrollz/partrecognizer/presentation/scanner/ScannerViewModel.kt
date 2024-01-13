package com.scrollz.partrecognizer.presentation.scanner

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrollz.partrecognizer.R
import com.scrollz.partrecognizer.domain.model.Report
import com.scrollz.partrecognizer.domain.model.Response
import com.scrollz.partrecognizer.domain.use_cases.AnalyzeImageUseCase
import com.scrollz.partrecognizer.domain.use_cases.DeleteReportUseCase
import com.scrollz.partrecognizer.domain.use_cases.SaveReportUseCase
import com.scrollz.partrecognizer.domain.use_cases.SaveImageUseCase
import com.scrollz.partrecognizer.utils.NetworkException
import com.scrollz.partrecognizer.utils.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val analyzeImageUseCase: AnalyzeImageUseCase,
    private val saveImageUseCase: SaveImageUseCase,
    private val saveReportUseCase: SaveReportUseCase,
    private val deleteReportUseCase: DeleteReportUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ScannerState())
    val state = _state.asStateFlow()

    private var response = Response("", emptyList(), "")
    private var id = -1L
    
    fun onEvent(event: ScannerEvent) {
        when (event) {
            is ScannerEvent.AnalyzeImage -> analyzeImage(event.imageProxy)
            is ScannerEvent.Save -> if (_state.value.isSaved) unsave() else save()
            is ScannerEvent.CloseImage -> _state.update { it.copy(openImage = null) }
            is ScannerEvent.ViewImage -> _state.update { it.copy(openImage = event.data) }
            is ScannerEvent.OpenDetailDialog -> openDetailDialog()
            is ScannerEvent.CloseDetailDialog -> _state.update {
                it.copy(isDetailDialogVisible = false, isSaved = false)
            }
            is ScannerEvent.ToggleTorch -> _state.update {
                it.copy(isTorchEnabled = !it.isTorchEnabled)
            }
            is ScannerEvent.ToggleCloseDialog -> _state.update {
                it.copy(
                    isCloseDialogVisible = !it.isCloseDialogVisible,
                    isSnackbarVisible = false
                )
            }
        }
    }

    private fun analyzeImage(imageProxy: ImageProxy) {
        if (_state.value.isDialogVisible) {
            imageProxy.close()
            return
        }
        viewModelScope.launch {
            analyzeImageUseCase(imageProxy)
                .onSuccess { response ->
                    _state.update { it.copy(error = null) }
                    if (!_state.value.isDialogVisible) {
                        this@ScannerViewModel.response = response
                        _state.update { state ->
                            state.copy(
                                isSnackbarVisible = true,
                                detectedDetails = response.detectedDetails.toImmutableList()
                            )
                        }
                    }
                }
                .onFailure { exception ->
                    val errorText = if (exception is NetworkException) {
                        UIText.StringResource(R.string.err_network)
                    } else {
                        UIText.StringResource(R.string.err_scanner)
                    }
                    _state.update { it.copy(isSnackbarVisible = false, error = errorText) }
                }
        }.invokeOnCompletion {
            imageProxy.close()
        }
    }

    private fun openDetailDialog() {
        _state.update { it.copy(isDetailDialogVisible = true, isSnackbarVisible = false) }
        viewModelScope.launch {
            saveImageUseCase(response.resultImage, response.dateTime)
                .onSuccess { resultImage -> _state.update { it.copy(resultImage = resultImage) } }
                .onFailure { _state.update { it.copy(resultImage = null) } }
        }
    }

    private fun save() {
        viewModelScope.launch {
            _state.update { it.copy(isSaveButtonEnabled = false) }
            id = saveReportUseCase(
                Report(
                    dateTime = response.dateTime,
                    resultImagePath = _state.value.resultImage,
                    detectedDetails = _state.value.detectedDetails.joinToString()
                )
            )
            _state.update { it.copy(isSaveButtonEnabled = true, isSaved = true) }
        }
    }

    private fun unsave() {
        viewModelScope.launch {
            _state.update { it.copy(isSaveButtonEnabled = false) }
            deleteReportUseCase(id)
            _state.update { it.copy(isSaveButtonEnabled = true, isSaved = false) }
        }
    }

}
