package com.am.genreclassifier.ui.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.core.state.ViewState
import com.am.genreclassifier.model.Genre
import com.am.genreclassifier.ui.PreviewConstants.ITEM_TRACK_STATE
import com.am.genreclassifier.ui.color.GradientColorEndItem
import com.am.genreclassifier.ui.color.GradientColorStart
import com.am.genreclassifier.ui.util.Padding
import com.am.genreclassifier.ui.util.Position

@Composable
fun TrackItem(pair: Pair<String, ViewState>) {
    val viewState = pair.second as ViewState.Success<*>
    val trackName = pair.first
    val genreName = (viewState.data as Genre).genre


    val brush = Brush.horizontalGradient(
        Pair(0f, GradientColorStart),
        Pair(1f, GradientColorEndItem)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(brush)
    ) {
        Padding(horizontal = 16.dp) {
            Position(position = Alignment.CenterStart) {
                Text(text = trackName, color = Color.White)
            }
        }
    }

}


@Preview(showSystemUi = true)
@Composable
fun PreviewTrackItem() {
    TrackItem(ITEM_TRACK_STATE)
}