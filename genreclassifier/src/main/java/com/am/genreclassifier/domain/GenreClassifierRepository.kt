package com.am.genreclassifier.domain

import com.am.core.state.ViewState
import com.am.genreclassifier.model.Track

interface GenreClassifierRepository {
    suspend fun scan(track: Track): ViewState
}