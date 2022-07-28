package com.am.genreclassifier.model

import com.am.core.util.DisplayableDate
import java.util.*

data class GenreModel(val id: UUID, val genre: String, val trackName: String, val displayableDate: DisplayableDate)
