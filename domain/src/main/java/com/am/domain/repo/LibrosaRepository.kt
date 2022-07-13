package com.am.domain.repo

import java.io.File

interface LibrosaRepository {
    suspend fun getStft(audioFileRes: Int): Array<FloatArray>
    suspend fun getStft(file: File): Array<FloatArray>
}