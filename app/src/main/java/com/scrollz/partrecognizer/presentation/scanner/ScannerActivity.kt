package com.scrollz.partrecognizer.presentation.scanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.scrollz.partrecognizer.presentation.scanner.components.ScannerScreen
import com.scrollz.partrecognizer.ui.theme.ScannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            ScannerTheme {
                val scannerViewModel = hiltViewModel<ScannerViewModel>()
                val scannerState by scannerViewModel.state.collectAsStateWithLifecycle()

                ScannerScreen(
                    modifier = Modifier.fillMaxSize(),
                    state = scannerState,
                    onEvent = scannerViewModel::onEvent,
                    closeScanner = {
                        setResult(Activity.RESULT_OK, Intent())
                        finish()
                    }
                )
            }
        }
    }
}
