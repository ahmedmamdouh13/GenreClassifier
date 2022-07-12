package com.am.genreclassifier.intent

import androidx.annotation.RawRes
import java.io.File

sealed class MainViewIntent {
    data class ScanTrackRawRes(@RawRes val rawRes: Int, val processId: String) : MainViewIntent()
    data class ScanTrackFile(val file: File, val processId: String) : MainViewIntent()
}