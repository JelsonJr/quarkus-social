package br.com.jelsonjr.util

fun getResponseErrorMap(error: String, message: String) =
    mapOf("error" to formatErrorName(error), "message" to message, "success" to false)

fun formatErrorName(error: String): String {
    var formattedError = error.replace("Exception", "")
    formattedError = formattedError.replace(Regex("([a-z])([A-Z])"), "$1 $2")

    return formattedError.trim()
}