package com.am.genreclassifier.domain

import com.am.core.state.ViewState
import com.am.genreclassifier.model.Track
import com.am.genreclassifier.state.MainViewState
import com.am.librosa.domain.LibrosaRepository
import java.io.File

class ClassifyTrackUseCaseImpl(
    private val librosaRepository: LibrosaRepository,
    private val classifierRepository: GenreClassifierRepository
) : ClassifyTrackUseCase {

    override suspend fun scan(track: Track): ViewState {
        return try {
            classifierRepository.scan(track)
        } catch (e: Exception) {
            ViewState.Error(e)
        }
    }

    override suspend fun scan(rawRes: Int, itemId: String): ViewState {
        return try {
            val stft = librosaRepository.getStft(rawRes)
            classifierRepository.scan(Track(stft, itemId))
        } catch (e: Exception) {
            ViewState.Error(e)
        }
    }

    override suspend fun scan(file: File, itemId: String): ViewState {
        return try {
            val stft = librosaRepository.getStft(file)
            classifierRepository.scan(Track(stft, itemId))
        } catch (e: Exception) {
            ViewState.Error(e)
        }
    }

}