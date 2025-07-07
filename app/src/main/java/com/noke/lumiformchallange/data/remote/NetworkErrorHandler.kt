package com.noke.lumiformchallange.data.remote

import com.noke.lumiformchallange.data.Result.*
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrorHandler {

    fun <T> handleError(throwable: Throwable): Error {
        return when (throwable) {
            is TimeoutCancellationException -> {
                Error(
                    AppNetworkException.TimeoutException(
                        "Request timeout - please check your connection and try again"
                    )
                )
            }

            is UnknownHostException -> {
                Error(
                    AppNetworkException.NoInternetException(
                        "No internet connection - please check your network settings"
                    )
                )
            }

            is ConnectException -> {
                Error(
                    AppNetworkException.ConnectionException(
                        "Connection failed - please check your internet connection"
                    )
                )
            }

            is SocketTimeoutException -> {
                Error(
                    AppNetworkException.TimeoutException(
                        "Connection timeout - slow network detected, please try again"
                    )
                )
            }

            is IOException -> {
                Error(
                    AppNetworkException.IOException(
                        "Network error - please check your connection and try again"
                    )
                )
            }

            is HttpException -> {
                handleHttpError(throwable)
            }

            else -> {
                Error(AppNetworkException.UnknownException("Unexpected error: ${throwable.message}"))
            }
        }
    }

    private fun handleHttpError(httpException: HttpException): Error {
        return when (val code = httpException.code()) {
            400 -> Error(AppNetworkException.ClientException("Bad request - invalid data sent to server"))
            401 -> Error(AppNetworkException.ClientException("Unauthorized - authentication required"))
            403 -> Error(AppNetworkException.ClientException("Access forbidden"))
            404 -> Error(AppNetworkException.ClientException("Content not found"))
            408 -> Error(AppNetworkException.TimeoutException("Request timeout - please try again"))
            429 -> Error(AppNetworkException.ClientException("Too many requests - please wait and try again"))
            in 500..599 -> Error(AppNetworkException.ServerException("Server error (${code}) - please try again later"))
            else -> Error(AppNetworkException.UnknownException("Network error (${code}) - please try again"))
        }
    }
}

sealed class AppNetworkException(message: String) : Exception(message) {
    class NoInternetException(message: String) : AppNetworkException(message)
    class TimeoutException(message: String) : AppNetworkException(message)
    class ConnectionException(message: String) : AppNetworkException(message)
    class IOException(message: String) : AppNetworkException(message)
    class ClientException(message: String) : AppNetworkException(message)
    class ServerException(message: String) : AppNetworkException(message)
    class UnknownException(message: String) : AppNetworkException(message)
}