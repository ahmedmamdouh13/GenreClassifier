package com.am.genreclassifier.ui.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun Position(position: Alignment, element: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = position) {
        element()
    }
}

@Composable
fun Padding(
    horizontal: Dp = 0.dp, vertical: Dp = 0.dp, start: Dp = 0.dp,
    end: Dp = 0.dp, top: Dp = 0.dp, bottom: Dp = 0.dp,
    element: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontal, vertical = vertical)
            .padding(start = start, top = top, end = end, bottom = bottom)

    ) {
        element()
    }
}