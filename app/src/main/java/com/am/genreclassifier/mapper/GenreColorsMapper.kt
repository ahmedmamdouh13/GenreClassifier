package com.am.genreclassifier.mapper

import androidx.compose.ui.graphics.Color
import com.am.genreclassifier.ui.theme.*
import com.am.genreclassifier.model.GenreType

fun GenreType.mapToColor(): Color {
    return when (this) {
        GenreType.ROCK -> ROCK_COLOR
        GenreType.CLASSICAL -> CLASSICAL_COLOR
        GenreType.DISCO -> DISCO_COLOR
        GenreType.COUNTRY -> COUNTRY_COLOR
        GenreType.HIPHOP -> HIPHOP_COLOR
        GenreType.BLUES -> BLUES_COLOR
        GenreType.METAL -> METAL_COLOR
        GenreType.REGGAE -> REGGAE_COLOR
        GenreType.POP -> POP_COLOR
        GenreType.JAZZ -> JAZZ_COLOR
        GenreType.LOADING -> IN_PROGRESS_COLOR
        else -> IDLE_COLOR
    }
}