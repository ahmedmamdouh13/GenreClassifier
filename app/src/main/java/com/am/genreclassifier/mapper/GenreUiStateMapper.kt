package com.am.genreclassifier.mapper

import com.am.core.state.ViewState
import com.am.core.util.DisplayableDate
import com.am.core.util.getCurrentDisplayableDate
import com.am.genreclassifier.model.Genre
import com.am.genreclassifier.state.GenreItemUiState
import java.util.*

class GenreUiStateMapper {

    suspend fun map(genreList: List<Genre>): Map<DisplayableDate, Map<UUID, GenreItemUiState>> {
        var previousDate = getCurrentDisplayableDate()
        var itemData = mutableMapOf<UUID, GenreItemUiState>()
        val genreResultDatedMap = mutableMapOf<DisplayableDate, Map<UUID, GenreItemUiState>>()

        genreList.forEach {
            itemData[it.processId] = it.mapToUiGenre(ViewState.Success)
            genreResultDatedMap[it.displayableDate] = itemData
            if (it.displayableDate != previousDate) {
                previousDate = it.displayableDate
                itemData = mutableMapOf()
            }
        }

        return genreResultDatedMap
    }

    suspend fun map(
        genreItemUiState: GenreItemUiState,
        previousDateMapGenreUiState: MutableMap<DisplayableDate, Map<UUID, GenreItemUiState>>
    ): MutableMap<DisplayableDate, Map<UUID, GenreItemUiState>> {

        val previousMapGenreItemUiState =
            previousDateMapGenreUiState[genreItemUiState.displayableDate]

        previousDateMapGenreUiState[genreItemUiState.displayableDate] =
            if (previousMapGenreItemUiState != null) {
                val previousMappedItem = previousMapGenreItemUiState.toMutableMap()
                previousMappedItem[genreItemUiState.id] = genreItemUiState
                previousMappedItem
            } else
                mapOf(Pair(genreItemUiState.id, genreItemUiState))

        return previousDateMapGenreUiState
    }


}