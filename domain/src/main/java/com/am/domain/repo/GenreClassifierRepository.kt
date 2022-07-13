package com.am.domain.repo

import com.am.core.state.ViewState
import com.am.domain.model.Track

interface GenreClassifierRepository {
    suspend fun scan(track: Track): ViewState
}