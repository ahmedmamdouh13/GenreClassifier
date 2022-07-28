package com.am.core.state

sealed class ViewState(){
    object Idle: ViewState()
    object Success: ViewState()
    object Error: ViewState()
    object Loading: ViewState()
}
