package com.am.domain.usecase

import com.am.core.state.ViewState
import com.am.domain.usecase.ClassifyTrackUseCase
import com.am.domain.repo.GenreClassifierRepository
import com.am.domain.model.Track
import com.am.domain.repo.LibrosaRepository
import java.io.File

class ClassifyTrackUseCaseImpl(
    private val librosaRepository: LibrosaRepository,
    private val classifierRepository: GenreClassifierRepository
) : ClassifyTrackUseCase {


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