package com.scrollz.partrecognizer.presentation.settings_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.scrollz.partrecognizer.R
import com.scrollz.partrecognizer.domain.model.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemePicker(
    modifier: Modifier = Modifier,
    theme: Theme,
    switchTheme: (Theme) -> Unit
) {

    val value by remember(theme) {
        derivedStateOf {
            when (theme) {
                Theme.Light -> 0f
                Theme.System -> 1f
                Theme.Dark -> 2f
            }
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.LightMode,
                contentDescription = stringResource(R.string.theme_light),
                tint = if (theme == Theme.Light) MaterialTheme.colorScheme.onBackground
                        else MaterialTheme.colorScheme.onSecondary
            )
            Slider(
                modifier = Modifier.weight(1f, fill = true),
                value = value,
                onValueChange = {
                    switchTheme(when (it) {
                        0f -> Theme.Light
                        2f -> Theme.Dark
                        else -> Theme.System
                    })
                },
                valueRange = 0f..2f,
                steps = 1,
                thumb = {
                    Surface(
                        modifier = Modifier.size(20.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary
                    ) {}
                },
                track = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp),
                            shape = RoundedCornerShape(100),
                            color = MaterialTheme.colorScheme.onSecondary
                        ) {}
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            repeat(3) {
                                Surface(
                                    modifier = Modifier.size(3.dp),
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.onSecondary
                                ) {}
                            }
                        }
                    }
                }
            )
            Icon(
                imageVector = Icons.Outlined.DarkMode,
                contentDescription = stringResource(R.string.theme_dark),
                tint = if (theme == Theme.Dark) MaterialTheme.colorScheme.onBackground
                        else MaterialTheme.colorScheme.onSecondary
            )
        }
        Text(
            text = stringResource(R.string.theme_system),
            style = MaterialTheme.typography.displaySmall,
            color = if (theme == Theme.System) MaterialTheme.colorScheme.onBackground
                    else MaterialTheme.colorScheme.onSecondary
        )
    }
}
