package com.am.genreclassifier.ui.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.am.genreclassifier.R
import com.am.genreclassifier.ui.util.Padding
import com.am.genreclassifier.ui.util.Position

@Composable
fun ToolbarItem() {
    Box(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.toolbar_height))
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
            .offset(y = (-2).dp)
            .shadow(elevation = 1.dp)
    ) {
        Padding(horizontal = dimensionResource(id = R.dimen.large_padding)) {
        Position(position = Alignment.CenterStart) {
                Text(text = stringResource(id = R.string.app_name), fontSize = 24.sp, color = MaterialTheme.colors.onPrimary)
            }
        }
    }
}