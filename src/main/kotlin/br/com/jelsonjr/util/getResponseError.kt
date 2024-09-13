package br.com.jelsonjr.util

data class ResponseError(
    val error: String,
    val message: String,
    val success: Boolean = false
)

fun getResponseErrorMap(error: String, message: String) =
    ResponseError(formatErrorName(error),message)

fun formatErrorName(error: String): String {
    var formattedError = error.replace("Exception", "")
    formattedError = formattedError.replace(Regex("([a-z])([A-Z])"), "$1 $2")

    return formattedError.trim()
}