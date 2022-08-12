package com.example.codeexample.data

enum class Source {
    LOCAL,
    REMOTE,
    DEFAULT
}

sealed class Resource<T>(val data: T? = null, val message: String? = null, source: Source) {
    class Success<T>(data: T, source: Source) : Resource<T>(data, source = source)
    class Error<T>(message: String, data: T? = null, source: Source) :
        Resource<T>(data, message, source = source)

    class Loading<T>(data: T? = null, source: Source = Source.DEFAULT) :
        Resource<T>(data, source = source)
}