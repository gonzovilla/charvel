package com.ust.charvel.network

sealed class Status<ResultType> {

    data class Success<ResultType>(
        val data: ResultType
    ) : Status<ResultType>()

    class Loading<ResultType> : Status<ResultType>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return true
        }

        override fun hashCode(): Int = javaClass.hashCode()
    }

    data class Error<ResultType>(
        val message: String
    ) : Status<ResultType>()

    companion object {

        fun <ResultType> success(data: ResultType): Status<ResultType> = Success(data)

        fun <ResultType> loading(): Status<ResultType> = Loading()

        fun <ResultType> error(message: String): Status<ResultType> = Error(message)

    }
}