package com.am.genreclassifier.ui.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.am.core.state.ViewState
import com.am.genreclassifier.R
import com.am.genreclassifier.mapper.mapToColor
import com.am.genreclassifier.ui.util.Padding
import com.am.genreclassifier.ui.util.Position
import com.am.genreclassifier.state.GenreItemUiState
import com.am.genreclassifier.model.GenreType
import com.am.genreclassifier.ui.theme.Typography
import java.util.*

@Composable
fun TrackItem(pair: Pair<UUID, GenreItemUiState>) {
    val genreName = pair.second.genre
    val trackName = pair.second.trackName
    val genreBackgroundColor = pair.second.genreColor

    val genreFont = when(pair.second.state){
        ViewState.Success -> Typography.subtitle1
        else -> Typography.subtitle2
    }
        Row(
            modifier = Modifier
                .height(60.dp)
                .background(
                    color = MaterialTheme.colors.surface,
                    RoundedCornerShape(4.dp)
                )
                .clip(RoundedCornerShape(4.dp))

        ) {

            Box(Modifier.weight(2.5f)) {
                Padding(horizontal = dimensionResource(id = R.dimen.small_padding)) {
                    Position(position = Alignment.CenterStart) {
                        Text(text = trackName, color = MaterialTheme.colors.onPrimary, fontSize = 12.sp)
                    }
                }
            }

            Box(
                Modifier
                    .weight(1.5f)
                    .background(color = genreBackgroundColor)
            ){
                    Position(position = Alignment.Center) {
                        Text(text = genreName, color = MaterialTheme.colors.onSurface,
                            style = genreFont)
                    }
            }
        }
//    }


}

@Preview(showSystemUi = true)
@Composable
fun PreviewTrackItem() {
//    TrackItem(ITEM_TRACK_STATE)
}