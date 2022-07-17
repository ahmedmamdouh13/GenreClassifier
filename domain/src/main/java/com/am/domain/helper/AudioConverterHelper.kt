package com.am.domain.helper

import com.am.core.state.ViewState
import java.io.File

interface AudioConverterHelper {
    suspend fun fileToWav(file: File): File
}
