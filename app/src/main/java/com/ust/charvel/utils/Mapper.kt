package com.ust.charvel.utils

interface Mapper<Storage, Remote> {
    fun Remote.toStorage(): Storage
    fun List<Remote>.toStorage(): List<Storage> = this.map { it.toStorage() }
}