package com.am.genreclassifier

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.genreclassifier.intent.MainViewIntent
import com.am.genreclassifier.state.MainViewState
import com.am.core.state.ViewState
import com.am.librosa.Librosa
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(
    private val genreClassifier: GenreClassifier,
    private val librosa: Librosa, private val context: Application
) : ViewModel() {

    private val _state: MutableStateFlow<MainViewState> = MutableStateFlow(MainViewState.Idle)

    private val list = mutableStateMapOf<String, ViewState>()

    val state: StateFlow<MainViewState>
        get() = _state

    val channel = Channel<MainViewIntent>(Int.MAX_VALUE)

    init {
        setUpChannel()
    }

    private fun setUpChannel() {
        viewModelScope.launch(Dispatchers.IO) {
            channel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is MainViewIntent.ScanTrackFile -> scan(intent.file, processId = intent.processId)
                    is MainViewIntent.ScanTrackRawRes -> {
                        render(ViewState.Loading("Loading"), processId = intent.processId)
                        scan(intent.rawRes, processId = intent.processId)
                    }
                }
            }
        }
    }

    private suspend fun scan(rawRes: Int, processId: String) {
            try {
                val stftFromRaw = librosa.getStft(context, rawRes)
                process(stftFromRaw, processId)
            } catch (e: Exception) {
                render(ViewState.Error(e), processId)
            }
    }

    private suspend fun process(stftFromRaw: Array<FloatArray>, processId: String) {
        val result = genreClassifier.scan(stftFromRaw, processId)
        render(result, processId)
    }

    private suspend fun render(result: ViewState, processId: String) {
        list[processId] = result
        _state.emit(MainViewState.Success(list))
    }

    private suspend fun scan(file: File, processId: String) {
            try {
                val stftFromRaw = librosa.getStft(file)
                process(stftFromRaw, processId)
            } catch (e: Exception) {
                render(ViewState.Error(e), processId)
            }
    }

}