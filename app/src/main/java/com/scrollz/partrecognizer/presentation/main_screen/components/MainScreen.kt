package com.scrollz.partrecognizer.presentation.main_screen.components

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.scrollz.partrecognizer.domain.model.Report
import com.scrollz.partrecognizer.presentation.common.PermissionDialog
import com.scrollz.partrecognizer.presentation.main_screen.MainEvent
import com.scrollz.partrecognizer.presentation.main_screen.MainState
import com.scrollz.partrecognizer.utils.ScannerContract
import com.scrollz.partrecognizer.utils.SettingsContract
import com.scrollz.partrecognizer.utils.toFineDateTime

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainState,
    reports: LazyPagingItems<Report>,
    onEvent: (MainEvent) -> Unit,
    navigateToSettings: () -> Unit,
    onReportClick: (Long) -> Unit
) {
    val context = LocalContext.current
    val isPermissionRequested = remember { mutableStateOf(false) }

    val scannerActivityResultLauncher = rememberLauncherForActivityResult(
        contract = ScannerContract(),
        onResult = { }
    )

    val settingsActivityResultLauncher = rememberLauncherForActivityResult(
        contract = SettingsContract(),
        onResult = { }
    )

    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                scannerActivityResultLauncher.launch(null)
            } else {
                if (!isPermissionRequested.value && shouldShowRequestPermissionRationale(
                        context as Activity, Manifest.permission.CAMERA
                    )
                ) {
                    isPermissionRequested.value = true
                } else {
                    onEvent(MainEvent.TogglePermissionDialog)
                }
            }
        }
    )

    if (state.isPermissionDialogVisible) {
        PermissionDialog(
            onDismiss = { onEvent(MainEvent.TogglePermissionDialog) },
            onConfirm = {
                onEvent(MainEvent.TogglePermissionDialog)
                settingsActivityResultLauncher.launch(null)
            }
        )
    }

    if (state.isDeleteDialogVisible) {
        DeleteDialog(
            selectedItemsCount = state.selectedReportsIds.size,
            onDismiss = { onEvent(MainEvent.ToggleDeleteDialog) },
            onConfirm = { onEvent(MainEvent.DeleteReports) },
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                isSelecting = state.isSelecting,
                selectedItemsCount = state.selectedReportsIds.size,
                cancelSelecting = { onEvent(MainEvent.CancelSelecting) },
                deleteReports = { onEvent(MainEvent.ToggleDeleteDialog) },
                navigateToSettings = navigateToSettings,
                launchScanner = {
                    cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
                }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(
                count = reports.itemCount,
                key = reports.itemKey{ report: Report -> report.id ?: -1 },
                contentType = reports.itemContentType { "report" }
            ) {index ->
                reports[index]?.let { report ->
                    ReportItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {
                                    if (state.isSelecting) {
                                        onEvent(MainEvent.SelectReport(report.id ?: -1))
                                    } else {
                                        onReportClick(report.id ?: -1)
                                    }
                                },
                                onLongClick = {
                                    onEvent(MainEvent.SelectReport(report.id ?: -1))
                                }
                            ),
                        selected = state.selectedReportsIds.containsKey(report.id),
                        details = report.detectedDetails,
                        dateTime = report.dateTime.toFineDateTime(),
                        resultImage = report.resultImagePath
                    )
                }
            }
        }
    }
}
