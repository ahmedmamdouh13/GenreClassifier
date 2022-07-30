package com.am.genreclassifier.ui.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import com.am.genreclassifier.R
import com.am.genreclassifier.state.GenreUiState
import com.am.genreclassifier.ui.util.Padding
import com.am.genreclassifier.ui.util.Position


@Composable
fun MainScreen(state: GenreUiState = remember {GenreUiState()}, onAddTrackClicked: () -> Unit, onScanClicked: () -> Unit) {
    Scaffold(topBar = {
        ToolbarItem()
    }
    ) {

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colors.primaryVariant,
                            MaterialTheme.colors.secondaryVariant
                        )
                    )
                )
        ) {

                Position(position = Alignment.TopCenter) {
                    TracksList(state.genreResultDatedMap)
                }


            Position(position = Alignment.Center) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (state.scanLoading.isActive) {
                        Text(text = state.scanLoading.msg)
                    }
                    if (state.scanError.isActive) {
                        Text(text = state.scanError.msg)
                    }
                }
            }


            Padding(vertical = dimensionResource(id = R.dimen.large_padding), horizontal = dimensionResource(id = R.dimen.small_padding)) {
                Position(Alignment.BottomEnd) {
                    AddButton(onAddTrackClicked)
                }
            }

            Padding(vertical = dimensionResource(id = R.dimen.large_padding)) {
                Position(position = Alignment.BottomCenter) {
                    ScanButtonItem(scanButton = state.scanButton, onScanClicked)
                }
            }
        }
    }
}


