package com.scrollz.partrecognizer.presentation.report_screen.components

import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun SlidingText(
    modifier: Modifier = Modifier,
    durationMillis: Int,
    text: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()
    val infiniteTransition = rememberInfiniteTransition(label = "offset")
    val offset = infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = scrollState.maxValue,
        typeConverter = TwoWayConverter(
            convertToVector = { value -> AnimationVector(value.toFloat()) },
            convertFromVector = { vector -> vector.value.toInt() }
        ),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, 2400, LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "offset"
    )

    LaunchedEffect(offset.value) {
        if (offset.value == 0) {
            scrollState.animateScrollTo(0, tween(400, 1000))
        } else {
            scrollState.scrollTo(offset.value)
        }
    }

    Row(
        modifier = modifier.horizontalScroll(scrollState, enabled = false),
    ) {
        text()
    }
}
