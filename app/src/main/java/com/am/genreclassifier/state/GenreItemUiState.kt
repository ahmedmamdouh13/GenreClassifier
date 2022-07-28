package com.am.genreclassifier.state

import androidx.compose.ui.graphics.Color
import com.am.core.state.ViewState
import com.am.core.util.DisplayableDate
import java.util.*

data class GenreItemUiState(
    val id: UUID,
    val genre: String,
    val trackName: String,
    val displayableDate: DisplayableDate,
    val state: ViewState,
    val genreColor: Color
)
