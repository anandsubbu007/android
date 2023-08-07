package com.subbu.invoice.utils

sealed class Result<out T> {
    object Idle : Result<Nothing>()
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T? = null) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()
}