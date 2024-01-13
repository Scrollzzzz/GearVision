package com.scrollz.partrecognizer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.scrollz.partrecognizer.domain.model.Theme
import com.scrollz.partrecognizer.domain.repository.PreferencesRepository
import com.scrollz.partrecognizer.presentation.navigation.Navigation
import com.scrollz.partrecognizer.ui.theme.PartRecognizerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            val theme by preferences.theme.collectAsStateWithLifecycle(Theme.System)
            PartRecognizerTheme(darkTheme = theme.darkTheme()) {
                Navigation()
            }
        }
    }
}
