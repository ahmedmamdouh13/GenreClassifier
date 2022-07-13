package com.am.genreclassifier.data

import com.am.core.state.ViewState
import com.am.genreclassifier.GenreClassifier
import com.am.genreclassifier.model.Track
import com.am.genreclassifier.domain.GenreClassifierRepository

class GenreClassifierRepositoryImpl(val genreClassifier: GenreClassifier) :
    GenreClassifierRepository {

    override suspend fun scan(track: Track): ViewState {
        return try {
            val genre = genreClassifier.scan(track)
            ViewState.Success(genre)
        } catch (e: Exception) {
            ViewState.Error(e)
        }

    }
}