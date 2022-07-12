package com.am.genreclassifier.state

import com.am.core.state.ViewState


sealed class MainViewState(){
    object Idle: MainViewState()
    data class Success(val state: Map<String, ViewState>): MainViewState()
    data class Error(val e: Exception): MainViewState()
    data class Loading(val msg: String): MainViewState()
}
