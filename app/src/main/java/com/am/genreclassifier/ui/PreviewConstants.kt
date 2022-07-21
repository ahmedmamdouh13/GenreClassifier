package com.am.genreclassifier.ui

import com.am.core.state.ViewState
import com.am.genreclassifier.model.Genre

object PreviewConstants {
    val ITEM_TRACK_STATE = Pair("thisrock", ViewState.Success<Genre>(Genre("Rock", "thisrock")))

}