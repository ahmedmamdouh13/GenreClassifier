package com.am.genreclassifier.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateMapOf
import com.am.core.model.ViewEffect
import com.am.core.state.ViewState
import com.am.core.util.DisplayableDate
import com.am.genreclassifier.Constants.DISPLAYABLE_ERROR_MESSAGE
import com.am.genreclassifier.Constants.SCAN_BUTTON_DEFAULT_TEXT
import java.util.*


@Stable
data class GenreUiState(
    val genreResultDatedMap: Map<DisplayableDate, Map<UUID, GenreItemUiState>> = mapOf(),
    val scanError: ViewEffect = ViewEffect(false, DISPLAYABLE_ERROR_MESSAGE),
    val scanLoading: ViewEffect = ViewEffect(false, DISPLAYABLE_ERROR_MESSAGE),
    val scanButton: ScanButton = ScanButton(SCAN_BUTTON_DEFAULT_TEXT, false),
    val listScansPlaceHolder: ViewState = ViewState.Idle
)
