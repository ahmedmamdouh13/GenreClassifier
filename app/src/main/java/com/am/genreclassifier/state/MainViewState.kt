package com.am.genreclassifier.state

import androidx.compose.runtime.mutableStateMapOf
import com.am.core.model.ViewEffect
import com.am.core.state.ViewState
import com.am.genreclassifier.Constants.SCAN_BUTTON_DEFAULT_TEXT
import com.am.genreclassifier.Constants.DISPLAYABLE_ERROR_MESSAGE
import com.am.genreclassifier.model.ScanButton


data class MainViewState(
    val genreResultList: MutableMap<String, ViewState> = mutableStateMapOf(),
    val scanError: ViewEffect = ViewEffect(false, DISPLAYABLE_ERROR_MESSAGE),
    val scanLoading: ViewEffect = ViewEffect(false, DISPLAYABLE_ERROR_MESSAGE),
    val scanButton: ScanButton = ScanButton(SCAN_BUTTON_DEFAULT_TEXT, false)
)
