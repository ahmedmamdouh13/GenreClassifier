package com.am.genreclassifier

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.core.model.ViewEffect
import com.am.genreclassifier.intent.MainViewIntent
import com.am.genreclassifier.state.MainViewState
import com.am.core.state.ViewState
import com.am.domain.usecase.ClassifyTrackUseCase
import com.am.genreclassifier.helper.ChooseAudioFileHelper
import com.am.genreclassifier.ui.util.getCurrentDisplayableDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: ClassifyTrackUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<MainViewState> = MutableStateFlow(MainViewState())

    val state: StateFlow<MainViewState>
        get() = _state

    val channel = Channel<MainViewIntent>(Channel.UNLIMITED)
    private val data = mutableStateMapOf<String, ViewState>()

    init {
        setUpChannel()
    }

    private fun chooseFileFromStorage(chooseAudioFileHelper: ChooseAudioFileHelper) {
        chooseAudioFileHelper.chooseFromStorage { waveFile, fileName ->
            viewModelScope.launch {
                channel.send(MainViewIntent.ScanTrackFile(file = waveFile, fileName))
            }
        }
    }

    private fun setUpChannel() {
        viewModelScope.launch(Dispatchers.IO) {
            channel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is MainViewIntent.ChooseTrackFile -> chooseFileFromStorage(intent.chooseAudioFileHelper)
                    is MainViewIntent.ScanTrackFile,
                    is MainViewIntent.ScanTrackRawRes -> scan(intent)
                }
            }
        }
    }

    private fun render(reducer: (MainViewState) -> MainViewState) {
        _state.value = reducer(_state.value)
    }


    private fun onItemError(e: Exception, itemId: String) {
        render {
            it.copy(scanError = ViewEffect(true, e.localizedMessage!!))
        }
    }

    private fun onItemResult(result: ViewState, itemId: String) {
        render { viewState ->

            data[itemId] = result

            viewState.genreResultDatedMap[getCurrentDisplayableDate()] = ViewState.Success(data)

            viewState
        }
    }

    private fun onItemLoading(itemId: String) {
        render { viewState ->
            data[itemId] = ViewState.Loading("****")
            viewState.genreResultDatedMap[getCurrentDisplayableDate()] = ViewState.Success(data)
            viewState
        }
    }


    private suspend fun scan(intent: MainViewIntent) {
        val itemId = getItemId(intent)

        onItemLoading(itemId)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getScanResult(intent)
                onItemResult(result, itemId)
            } catch (e: Exception) {
                e.printStackTrace()
                onItemError(e, itemId)
            }
        }
    }

    private suspend fun getScanResult(intent: MainViewIntent): ViewState {
        return when (intent) {
            is MainViewIntent.ScanTrackFile -> useCase.scan(intent.file, intent.processId)
            is MainViewIntent.ScanTrackRawRes -> useCase.scan(intent.rawRes, intent.processId)
            else -> ViewState.Idle
        }
    }

    private fun getItemId(intent: MainViewIntent): String {
        return when (intent) {
            is MainViewIntent.ScanTrackFile -> intent.processId
            is MainViewIntent.ScanTrackRawRes -> intent.processId
            else -> ""
        }
    }

}