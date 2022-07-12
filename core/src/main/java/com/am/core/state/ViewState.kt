package com.am.core.state

sealed class ViewState(){
    object Idle: ViewState()
    data class Success<T>(val data: T): ViewState()
    data class Error(val e: Exception): ViewState()
    data class Loading(val msg: String): ViewState()
}
