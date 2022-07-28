package com.am.core.state


sealed class Result<out R>{
  data class Success<out T>(val data: T): Result<T>()
  data class Error(val e: Exception): Result<Nothing>()
}
