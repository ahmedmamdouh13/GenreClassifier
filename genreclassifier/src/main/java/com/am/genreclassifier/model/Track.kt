package com.am.genreclassifier.model

import com.am.core.util.DisplayableDate
import java.util.*

data class Track(val audioFloatArray: Array<FloatArray>, val trackId: UUID, val trackName: String, val displayableDate: DisplayableDate)
