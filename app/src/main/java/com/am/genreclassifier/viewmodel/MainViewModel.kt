package com.am.genreclassifier.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.core.model.ViewEffect
import com.am.core.state.Result
import com.am.core.state.ViewState
import com.am.core.util.getCurrentDisplayableDate
import com.am.genreclassifier.data.GenreClassifierRepository
import com.am.librosa.data.LibrosaRepository
import com.am.genreclassifier.helper.ChooseAudioFileHelper
import com.am.genreclassifier.intent.MainViewIntent
import com.am.genreclassifier.mapper.GenreUiStateMapper
import com.am.genreclassifier.mapper.mapToColor
import com.am.genreclassifier.mapper.mapToUiGenre
import com.am.genreclassifier.model.Genre
import com.am.genreclassifier.model.GenreType
import com.am.genreclassifier.state.GenreItemUiState
import com.am.genreclassifier.model.Track
import com.am.genreclassifier.state.GenreUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    private val genreClassifierRepo: GenreClassifierRepository,
    private val librosaRepo: LibrosaRepository,
    private val mapper: GenreUiStateMapper
) : ViewModel() {

    private val _state: MutableStateFlow<GenreUiState> = MutableStateFlow(GenreUiState())

    val state: StateFlow<GenreUiState>
        get() = _state

    val channel = Channel<MainViewIntent>(Channel.UNLIMITED)
//    private val data = mutableStateMapOf<UUID, GenreItemUiState>()

    private val pendingIntents = arrayListOf<MainViewIntent>()

    init {
        setUpChannel()
        getAllPreviousScans()
    }

    private fun getAllPreviousScans() {
        val size = 0
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = genreClassifierRepo.getAllGenres(size)) {
                is Result.Error -> {
                    render { mainViewState ->
                        mainViewState.copy(listScansPlaceHolder = ViewState.Error)
                    }
                }

                is Result.Success -> {
                    val mappedResult = mapper.map(result.data)

                    render { viewState ->
                        viewState.copy(genreResultDatedMap = mappedResult)
                    }
                }
            }


        }
    }

    private fun chooseFileFromStorage(chooseAudioFileHelper: ChooseAudioFileHelper) {
        chooseAudioFileHelper.chooseFromStorage { waveFile, fileName ->
            viewModelScope.launch {
                val randomUUID = UUID.randomUUID()
                onItemIdle(fileName, randomUUID)
                pendingIntents.add(MainViewIntent.ScanTrackFile(waveFile, randomUUID, fileName))
            }
        }
    }

    private suspend fun onScan() {
        pendingIntents.forEach {
            channel.send(it)
        }
        pendingIntents.clear()
    }

    private suspend fun onItemIdle(fileName: String, randomUUID: UUID) {

        val genreItemUiStateIdle = GenreItemUiState(
            randomUUID, GenreType.IDLE.displayableName, fileName,
            displayableDate = getCurrentDisplayableDate(),
            ViewState.Idle,
            GenreType.IDLE.mapToColor()
        )

        render { viewState ->
            val mappedResult =
                mapper.map(genreItemUiStateIdle, viewState.genreResultDatedMap.toMutableMap())

            viewState.copy(genreResultDatedMap = mappedResult)
        }
    }

    private fun setUpChannel() {
        viewModelScope.launch(Dispatchers.IO) {
            channel.consumeAsFlow().collect { intent ->
                when (intent) {
                    MainViewIntent.NextPage -> getAllPreviousScans()
                    is MainViewIntent.ChooseTrackFile -> chooseFileFromStorage(intent.chooseAudioFileHelper)
                    is MainViewIntent.ScanTrackFile,
                    is MainViewIntent.ScanTrackRawRes -> scan(intent)
                    MainViewIntent.Scan -> onScan()
                }
            }
        }
    }

    private suspend fun render(reducer: suspend (GenreUiState) -> GenreUiState) {
        _state.value = reducer(_state.value)
    }


    private suspend fun onItemError(e: Exception, itemId: String) {
        render {
            it.copy(scanError = ViewEffect(true, e.localizedMessage!!))
        }
    }

    private suspend fun onItemResult(result: Result<Genre>) {
        render { viewState ->
            val genreItemUiState = getGenre(result)

            val mappedResult =
                mapper.map(genreItemUiState, viewState.genreResultDatedMap.toMutableMap())

            viewState.copy(genreResultDatedMap = mappedResult)
        }
    }

    private fun getGenre(result: Result<Genre>): GenreItemUiState {
        when(result){
            is Result.Error -> {
                result.e.printStackTrace()
            }
            is Result.Success ->  result.data.mapToUiGenre(ViewState.Success)
        }
        return (result as Result.Success<Genre>).data.mapToUiGenre(ViewState.Success)
    }

    private suspend fun onItemLoading(genreItemUiState: GenreItemUiState) {
        render { viewState ->

            val genreItemUiStateLoading = genreItemUiState.copy(
                state = ViewState.Loading,
                genreColor = GenreType.LOADING.mapToColor(),
                genre = GenreType.LOADING.displayableName
            )

            val mappedResult = mapper.map(
                genreItemUiStateLoading, viewState.genreResultDatedMap.toMutableMap()
            )

            viewState.copy(genreResultDatedMap = mappedResult)
        }
    }


    private suspend fun scan(intent: MainViewIntent) {
        val item = getItem(intent)

        onItemLoading(item)

        viewModelScope.launch(Dispatchers.IO) {
            val result = getScanResult(intent)
            onItemResult(result)
        }
    }

    private fun getItem(intent: MainViewIntent): GenreItemUiState {
        return when (intent) {
            is MainViewIntent.ScanTrackFile -> GenreItemUiState(
                intent.processId,
                "",
                intent.trackName,
                getCurrentDisplayableDate(),
                ViewState.Idle,
                GenreType.IDLE.mapToColor()
            )
            is MainViewIntent.ScanTrackRawRes -> GenreItemUiState(
                intent.processId,
                "",
                intent.trackName,
                getCurrentDisplayableDate(),
                ViewState.Idle,
                GenreType.IDLE.mapToColor()
            )
            else -> GenreItemUiState(
                UUID.randomUUID(), "", "", getCurrentDisplayableDate(), ViewState.Idle,
                GenreType.IDLE.mapToColor()
            )
        }
    }

    private suspend fun getScanResult(intent: MainViewIntent): Result<Genre> {
        return when (intent) {
            is MainViewIntent.ScanTrackFile -> {
                val stft = librosaRepo.getStft(intent.file)
                val track =
                    Track(stft, intent.processId, intent.trackName, getCurrentDisplayableDate())
                genreClassifierRepo.scan(track)
            }
            is MainViewIntent.ScanTrackRawRes -> {
                val stft = librosaRepo.getStft(intent.rawRes)
                val track =
                    Track(stft, intent.processId, intent.trackName, getCurrentDisplayableDate())
                genreClassifierRepo.scan(track)
            }
            else -> {
                Result.Success(Genre(UUID.randomUUID(), "", "", ""))
            }
        }
    }

    private fun getItemId(intent: MainViewIntent): UUID {
        return when (intent) {
            is MainViewIntent.ScanTrackFile -> intent.processId
            is MainViewIntent.ScanTrackRawRes -> intent.processId
            else -> UUID.randomUUID()
        }
    }

}