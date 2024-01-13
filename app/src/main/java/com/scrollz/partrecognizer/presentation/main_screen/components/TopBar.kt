package com.scrollz.partrecognizer.presentation.main_screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.scrollz.partrecognizer.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    modifier: Modifier = Modifier,
    isSelecting: Boolean,
    selectedItemsCount: Int,
    cancelSelecting: () -> Unit,
    deleteReports: () -> Unit,
    navigateToSettings: () -> Unit,
    launchScanner: () -> Unit
) {
    Crossfade(
        targetState = isSelecting,
        animationSpec = tween(400),
        label = "top_bar"
    ) { selecting ->
        if (selecting) {
            TopAppBar(
                modifier = modifier,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                title = {
                    AnimatedContent(
                        targetState = selectedItemsCount,
                        transitionSpec = {
                            slideInVertically(tween(200)) { -it } togetherWith
                            slideOutVertically(tween(200)) { it }
                        },
                        label = "counter"
                    ) { targetCount ->
                        if (targetCount > 0) {
                            Text(
                                text = "$targetCount",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = cancelSelecting) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.button_close)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = deleteReports) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(R.string.button_delete)
                        )
                    }
                }
            )
        } else {
            CenterAlignedTopAppBar(
                modifier = modifier,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.settings)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = launchScanner) {
                        Icon(
                            imageVector = Icons.Outlined.Videocam,
                            contentDescription = stringResource(R.string.button_scanner)
                        )
                    }
                }
            )
        }
    }
}
