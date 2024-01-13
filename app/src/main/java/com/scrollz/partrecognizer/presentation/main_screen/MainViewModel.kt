package com.scrollz.partrecognizer.presentation.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.scrollz.partrecognizer.domain.use_cases.DeleteReportsUseCase
import com.scrollz.partrecognizer.domain.use_cases.GetReportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getReports: GetReportsUseCase,
    private val deleteReportsUseCase: DeleteReportsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    val reports = getReports().cachedIn(viewModelScope)

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SelectReport -> selectReport(event.id)
            is MainEvent.CancelSelecting -> cancelSelecting()
            is MainEvent.DeleteReports -> deleteReports()
            is MainEvent.ToggleDeleteDialog -> _state.update {
                it.copy(isDeleteDialogVisible = !it.isDeleteDialogVisible)
            }
            is MainEvent.TogglePermissionDialog -> _state.update {
                it.copy(isPermissionDialogVisible = !it.isPermissionDialogVisible)
            }
        }
    }

    private fun selectReport(id: Long) {
        _state.update { state ->
            if (state.selectedReportsIds.containsKey(id)) {
                state.selectedReportsIds.remove(id)
                state.copy(isSelecting = state.selectedReportsIds.isNotEmpty())
            } else {
                state.selectedReportsIds[id] = true
                state.copy(isSelecting = true)
            }
        }
    }

    private fun cancelSelecting() {
        _state.update { state ->
            state.selectedReportsIds.clear()
            state.copy(isSelecting = false, isDeleteDialogVisible = false)
        }
    }

    private fun deleteReports() {
        val ids = _state.value.selectedReportsIds.keys.toSet()
        viewModelScope.launch { deleteReportsUseCase(ids) }
        cancelSelecting()
    }

}
