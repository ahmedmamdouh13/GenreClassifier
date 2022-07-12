package com.am.librosa

import android.content.Context
import androidx.annotation.RawRes
import kotlin.math.ceil

object Librosa {
  private const val n_mfcc = 13
  private const val hopLength = 512
  private const val sampleRate = 22050
  private const val duration = 3
  private const val samplesPerTrack = sampleRate * duration
  private const val numSegments = 5
  private const val numSamplesPerSegment: Int = samplesPerTrack / numSegments
  private val expectedMfccVectorsPerSegment =
        ceil((numSamplesPerSegment.toDouble() / hopLength)).toInt()

   fun getStftFromRaw(context: Context, @RawRes audioFileRes: Int): Array<FloatArray> {

        val jLibrosa = JLibrosa()

        val arrayOfFloatArray = arrayListOf<FloatArray>()

        val audioFloatArray = jLibrosa.loadAndRead(
            "",
            -1,
            -1,
            context,
            audioFileRes
        )

        for (s in 0 until numSegments) {
            val start = numSamplesPerSegment * s
            val end = start + numSamplesPerSegment

            var mfcc = jLibrosa.generateMFCCFeatures(
                audioFloatArray.copyOfRange(start, end),
                sampleRate,
                n_mfcc
            )

            mfcc = getTransposeOf(mfcc)


            if (mfcc.size == expectedMfccVectorsPerSegment) {
                mfcc.forEach {
                    arrayOfFloatArray.add(it)
                }
            }

        }
        val toTypedArray = arrayOfFloatArray.toTypedArray()

        return toTypedArray
    }


    private fun getTransposeOf(matrix: Array<FloatArray>): Array<FloatArray> {

        val row = matrix.size
        val column = matrix[0].size

        // Transpose the matrix
        val transpose = Array(column) { FloatArray(row) }
        for (i in 0 until row) {
            for (j in 0 until column) {
                transpose[j][i] = matrix[i][j]
            }
        }
        return transpose
    }


//
//    private fun getTestArray(): Array<FloatArray> {
//        val inputStream = resources.assets.open("input_genre_test2.txt")
//        val reader = inputStream.reader()
//        val readText = reader.readText()
//        var floatList = readText.lines().flatMap { list ->
//            list.split(" ").map {
//                if (it.isNotBlank() || it.isNotEmpty())
//                    it.toFloat()
//                else -9000f
//            }
//        }
//        floatList = floatList.filterIndexed { index, fl ->
//            fl != -9000f
//        }
//
//        println("Am i better?: ${floatList.size}")
//
//        return arrayOf(floatList.toFloatArray())
//    }


}