package com.am.genreclassifier.domain

import com.am.core.state.ViewState
import com.am.genreclassifier.model.Track
import java.io.File

interface ClassifyTrackUseCase {
   suspend fun scan(track: Track): ViewState
   suspend fun scan(rawRes: Int, itemId: String): ViewState
   suspend fun scan(file: File, itemId: String): ViewState
}