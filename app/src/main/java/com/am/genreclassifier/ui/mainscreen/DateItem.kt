package com.am.genreclassifier.ui.mainscreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.am.core.util.DisplayableDate

@Composable
fun DateHeaderItem(date: DisplayableDate){
    Text(text = date, Modifier.fillMaxWidth().wrapContentHeight(), textAlign = TextAlign.Center
    , fontStyle = FontStyle.Italic, color = MaterialTheme.colors.onBackground
    , fontSize = 10.sp
    )
}