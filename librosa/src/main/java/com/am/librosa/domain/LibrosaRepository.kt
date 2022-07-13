package com.am.librosa.domain

import android.content.Context
import androidx.annotation.RawRes
import java.io.File

interface LibrosaRepository {
    suspend fun getStft(@RawRes audioFileRes: Int): Array<FloatArray>
    suspend fun getStft(file: File): Array<FloatArray>
}