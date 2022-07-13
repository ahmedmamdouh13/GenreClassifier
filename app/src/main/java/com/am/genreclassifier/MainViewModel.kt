package com.am.genreclassifier

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.genreclassifier.intent.MainViewIntent
import com.am.genreclassifier.state.MainViewState
import com.am.core.state.ViewState
import com.am.genreclassifier.domain.ClassifyTrackUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(
    private val useCase: ClassifyTrackUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<MainViewState> = MutableStateFlow(MainViewState.Idle)

    private val stateMap = mutableStateMapOf<String, ViewState>()

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
                    is MainViewIntent.ScanTrackFile -> scan(
                        intent.file,
                        itemId = intent.processId
                    )
                    is MainViewIntent.ScanTrackRawRes -> {
                        stateMap[intent.processId] = ViewState.Loading("Loading genre!")
                        render { MainViewState.Success(stateMap) }
                        scan(intent.rawRes, itemId = intent.processId)
                    }
                }
            }
        }
    }

    private fun render(reducer: (MainViewState) -> MainViewState) {
        _state.value = reducer(_state.value)
    }

    private suspend fun scan(rawRes: Int, itemId: String) {
        try {
            val result = useCase.scan(rawRes, itemId)
            stateMap[itemId] = result
            render { MainViewState.Success(stateMap) }
        } catch (e: Exception) {
            render { MainViewState.Error(e) }
        }
    }

    private suspend fun scan(file: File, itemId: String) {
        try {
            val result = useCase.scan(file, itemId)
            stateMap[itemId] = result
            render { MainViewState.Success(stateMap) }
        } catch (e: Exception) {
            render { MainViewState.Error(e) }
        }
    }

}