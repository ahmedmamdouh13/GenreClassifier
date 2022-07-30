package com.am.genreclassifier.ui.mainscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import com.am.core.util.DisplayableDate
import com.am.genreclassifier.R
import com.am.genreclassifier.state.GenreItemUiState
import com.am.genreclassifier.ui.util.Padding
import java.util.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TracksList(genreResultDatedMap: Map<DisplayableDate, Map<UUID, GenreItemUiState>>) {


    LazyColumn(state = rememberLazyListState(), reverseLayout = true) {

        genreResultDatedMap.forEach { (date, state) ->

            stickyHeader {
//                Padding(vertical = dimensionResource(id = R.dimen.small_padding)) {
                    DateHeaderItem(date = date)
//                }
            }

            val toList = state.toList()

            items(toList.size, key = { toList[it].first }) { item ->

                Padding(
                    vertical = dimensionResource(id = R.dimen.smaller_padding),
                    horizontal = dimensionResource(id = R.dimen.small_padding)) {
                    TrackItem(pair = toList[item])
                }
            }
        }

    }

}
