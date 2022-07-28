package com.am.genreclassifier.data

import com.am.core.state.Result
import com.am.core.state.ViewState
import com.am.genreclassifier.model.Genre
import com.am.genreclassifier.model.Track

interface GenreClassifierRepository {
    suspend fun scan(track: Track): Result<Genre>
    suspend fun getAllGenres(from: Int): Result<List<Genre>>
}