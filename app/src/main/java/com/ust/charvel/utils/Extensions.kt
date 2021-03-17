package com.ust.charvel.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

fun String.md5(): String {
    val MD5 = "MD5"
    try {
        // Create MD5 Hash
        val digest: MessageDigest = MessageDigest
            .getInstance(MD5)
        digest.update(this.toByteArray())
        val messageDigest: ByteArray = digest.digest()
        // Create Hex String
        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2) h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}