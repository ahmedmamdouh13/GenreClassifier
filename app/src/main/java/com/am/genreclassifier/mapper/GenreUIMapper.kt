package com.am.genreclassifier.mapper

import androidx.compose.ui.text.toUpperCase
import com.am.core.state.ViewState
import com.am.genreclassifier.model.Genre
import com.am.genreclassifier.model.GenreType
import com.am.genreclassifier.state.GenreItemUiState


fun Genre.mapToUiGenre(state: ViewState): GenreItemUiState {
    return GenreItemUiState(this.processId, this.genre,
        this.trackName, this.displayableDate, state,
        GenreType.valueOf(this.genre.uppercase()).mapToColor())
}


fun GenreItemUiState.mapToDomainModel(): Genre {
    return Genre(this.id, this.genre, this.trackName, this.displayableDate)
}


