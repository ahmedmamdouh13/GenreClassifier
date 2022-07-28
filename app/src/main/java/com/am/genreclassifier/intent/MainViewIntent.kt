package com.am.genreclassifier.intent

import androidx.annotation.RawRes
import com.am.genreclassifier.helper.ChooseAudioFileHelper
import java.io.File
import java.util.*

sealed class MainViewIntent {
    data class ScanTrackRawRes(@RawRes val rawRes: Int, val processId: UUID, val trackName: String) : MainViewIntent()
    data class ScanTrackFile(val file: File, val processId: UUID, val trackName: String) : MainViewIntent()
    data class ChooseTrackFile(val chooseAudioFileHelper: ChooseAudioFileHelper) : MainViewIntent()
    object Scan : MainViewIntent()
    object NextPage : MainViewIntent()
}