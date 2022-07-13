package com.am.domain.usecase

import com.am.core.state.ViewState
import java.io.File

interface ClassifyTrackUseCase {
   suspend fun scan(rawRes: Int, itemId: String): ViewState
   suspend fun scan(file: File, itemId: String): ViewState
}