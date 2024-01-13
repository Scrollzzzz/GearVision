package com.scrollz.partrecognizer.presentation.settings_screen.components

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.scrollz.partrecognizer.R
import com.scrollz.partrecognizer.presentation.common.PermissionDialog
import com.scrollz.partrecognizer.presentation.settings_screen.SettingsEvent
import com.scrollz.partrecognizer.presentation.settings_screen.SettingsState
import com.scrollz.partrecognizer.presentation.settings_screen.SettingsUIEvent
import com.scrollz.partrecognizer.utils.SettingsContract

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val isPermissionRequested = remember { mutableStateOf(false) }

    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result ->
            result.contents?.let { onEvent(SettingsEvent.SaveBaseUrlQR(it)) }
        }
    )

    val settingsActivityResultLauncher = rememberLauncherForActivityResult(
        contract = SettingsContract(),
        onResult = { }
    )

    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                scannerLauncher.launch(
                    ScanOptions()
                        .setOrientationLocked(false)
                        .setDesiredBarcodeFormats(BarcodeFormat.QR_CODE.name)
                )
            } else {
                if (!isPermissionRequested.value && ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity, Manifest.permission.CAMERA
                    )
                ) {
                    isPermissionRequested.value = true
                } else {
                    onEvent(SettingsEvent.TogglePermissionDialog)
                }
            }
        }
    )

    LaunchedEffect(state.uiEvent) {
        state.uiEvent?.let { uiEvent ->
            when (uiEvent) {
                is SettingsUIEvent.ShowSnackbar -> snackbarHostState.showSnackbar(
                    uiEvent.message.asString(context)
                )
            }
            onEvent(SettingsEvent.ConsumeUIEvent)
        }
    }

    if (state.isPermissionDialogVisible) {
        PermissionDialog(
            onDismiss = { onEvent(SettingsEvent.TogglePermissionDialog) },
            onConfirm = {
                onEvent(SettingsEvent.TogglePermissionDialog)
                settingsActivityResultLauncher.launch(null)
            }
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = { TopBar(navigateBack = navigateBack) },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = {
                    Snackbar(
                        modifier = Modifier.padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ) {
                        Text(
                            text = it.visuals.message,
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 64.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.domain,
                label = stringResource(R.string.label_ip),
                onValueChange = { value -> onEvent(SettingsEvent.ChangeIP(value)) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.port,
                label = stringResource(R.string.label_port),
                onValueChange = { value -> onEvent(SettingsEvent.ChangePort(value)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier.widthIn(min = 100.dp),
                    onClick = { cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA) },
                    enabled = state.isButtonsEnabled,
                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                        disabledContentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCode2,
                        contentDescription = stringResource(R.string.button_qr)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.button_qr),
                        style = MaterialTheme.typography.displaySmall
                    )
                }
                Button(
                    modifier = Modifier.widthIn(min = 100.dp),
                    onClick = { onEvent(SettingsEvent.SaveBaseUrl) },
                    enabled = state.isButtonsEnabled,
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                        disabledContentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.button_save),
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(128.dp))
            ThemePicker(
                modifier = Modifier.fillMaxWidth(),
                theme = state.theme,
                switchTheme = { theme -> onEvent(SettingsEvent.SwitchTheme(theme)) }
            )
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}
