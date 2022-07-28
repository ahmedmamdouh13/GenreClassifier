package com.am.librosa.data

import android.content.Context
import androidx.annotation.RawRes
import com.am.librosa.data.util.JLibrosa
import com.am.librosa.data.util.LibrosaHelper
import java.io.File

class LibrosaRepositoryImpl(private val librosa: LibrosaHelper,
                            private  val jLibrosa: JLibrosa,
                            private val context: Context): LibrosaRepository {

    @Synchronized
   override fun getStft(@RawRes audioFileRes: Int): Array<FloatArray> {
        val audioFloatArray = jLibrosa.loadAndRead(
            "",
            -1,
            -1,
            context,
            audioFileRes
        )

        return librosa.getStft(audioFloatArray, jLibrosa)
    }

    @Synchronized
    override fun getStft(file: File): Array<FloatArray> {
        val audioFloatArray = jLibrosa.loadAndRead(file, -1, -1)

        return librosa.getStft(audioFloatArray,
            JLibrosa()
        )
    }

}