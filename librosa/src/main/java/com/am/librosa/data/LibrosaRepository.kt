package com.am.librosa.data

import java.io.File

interface LibrosaRepository {
    fun getStft(audioFileRes: Int): Array<FloatArray>
    fun getStft(file: File): Array<FloatArray>
}