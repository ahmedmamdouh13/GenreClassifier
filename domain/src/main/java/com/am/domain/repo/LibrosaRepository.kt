package com.am.domain.repo

import java.io.File

interface LibrosaRepository {
    fun getStft(audioFileRes: Int): Array<FloatArray>
    fun getStft(file: File): Array<FloatArray>
}