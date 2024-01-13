package com.scrollz.partrecognizer.domain.model

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
sealed class Theme(val value: Int) {
    data object Dark: Theme(-1)
    data object Light: Theme(1)
    data object System: Theme(0)

    @Composable
    fun darkTheme(): Boolean = when (this) {
        Dark -> true
        Light -> false
        System -> isSystemInDarkTheme()
    }

}
