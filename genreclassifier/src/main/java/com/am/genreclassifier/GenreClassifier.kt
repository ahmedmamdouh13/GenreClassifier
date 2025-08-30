package com.am.genreclassifier

import android.content.Context
import com.am.genreclassifier.model.Genre
import com.am.genreclassifier.model.Track
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.common.FileUtil

class GenreClassifier(ctx: Context) {
    companion object {
        private const val MODEL_PATH = "genre_classifier1000.tflite"
        val predictionLabels = arrayOf(
            "disco",
            "classical",
            "country",
            "blues",
            "metal",
            "rock",
            "reggae",
            "pop",
            "hiphop",
            "jazz"
        )
    }

    private val nnApiDelegate by lazy {
        NnApiDelegate()
    }

    private val tflite by lazy(mode = LazyThreadSafetyMode.PUBLICATION) {
        Interpreter(
            FileUtil.loadMappedFile(ctx, MODEL_PATH),
            Interpreter.Options().addDelegate(nnApiDelegate)
        )
    }


    private fun run(input: Array<FloatArray>, output: Array<FloatArray>) {
        tflite.run(input, output)
    }

    private fun runForMultipleInputsOutputs(stft: Array<FloatArray>, output: Map<Int, Any>) {
        tflite.runForMultipleInputsOutputs(stft, output)
    }

    fun scan(track: Track): Genre {
        val output = arrayOf(FloatArray(predictionLabels.size))
        var result = ""
        run(track.audioFloatArray, output)
        output.forEach {

            val maxOfValues = it.maxOf {
                it
            }
            val indexOfFirst = it.indexOfFirst {
                it == maxOfValues
            }

            result = predictionLabels[indexOfFirst]
        }

        return Genre(track.trackId, result, track.trackName, track.displayableDate)
    }
}
