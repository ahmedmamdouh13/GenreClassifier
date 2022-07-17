package com.am.genreclassifier

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.core.model.ViewEffect
import com.am.genreclassifier.intent.MainViewIntent
import com.am.genreclassifier.state.MainViewState
import com.am.core.state.ViewState
import com.am.domain.usecase.ClassifyTrackUseCase
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

    private val _state: MutableStateFlow<MainViewState> = MutableStateFlow(MainViewState())

    val state: StateFlow<MainViewState>
        get() = _state

    val channel = Channel<MainViewIntent>(Channel.UNLIMITED)

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
                        render {
                            it.copy(scanLoading = ViewEffect(true, "Loading.."))
                        }
                        scan(intent.rawRes, itemId = intent.processId)
                    }
                    MainViewIntent.ChooseTrackFile -> {


                    }
                }
            }
        }
    }

    private fun render(reducer: (MainViewState) -> MainViewState) {
        _state.value = reducer(_state.value)
    }

    private suspend fun scan(rawRes: Int, itemId: String) {
        render {
            it.genreResultList[itemId] = ViewState.Loading("*****")
            it
        }
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val result = useCase.scan(rawRes, itemId)

                render {
                    it.genreResultList[itemId] = result
                    it.copy(scanLoading = ViewEffect(false))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                render {
                    it.copy(scanError = ViewEffect(true, e.localizedMessage!!))
                }
            }
        }
    }

    private suspend fun scan(file: File, itemId: String) {
        render {
            it.genreResultList[itemId] = ViewState.Loading("*****")
            it
        }
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val result = useCase.scan(file, itemId)

                render {
                    it.genreResultList[itemId] = result
                    it.copy(scanLoading = ViewEffect(false))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                render {
                    it.copy(scanError = ViewEffect(true, e.localizedMessage!!))
                }
            }
        }
    }

}