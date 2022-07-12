package com.am.genreclassifier

import android.content.Context
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.common.FileUtil
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class GenreClassifier(ctx: Context) {
    companion object {
        private const val ACCURACY_THRESHOLD = 0.5f
        private const val MODEL_PATH = "genre_classifier1000.tflite"
        private const val LABELS_PATH = "genre_classifier_model.txt"
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

    private val tflite by lazy {
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

    fun scan(stft: Array<FloatArray>): String {
        val output = arrayOf(FloatArray(predictionLabels.size))
        var result = ""

        run(stft, output)

        output.forEach {

            val maxOfValues = it.maxOf {
                it
            }
            val indexOfFirst = it.indexOfFirst {
                it == maxOfValues
            }

            result = predictionLabels[indexOfFirst]
        }
        return result
    }
}
