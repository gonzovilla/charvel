package com.ust.charvel.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.ust.charvel.R

object CustomDialog {
    fun show(context: Context, textToShow: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.dialog_title))
        builder.setMessage(textToShow)
        builder.setPositiveButton(context.getString(R.string.ok)) { _, _ -> }
        builder.show()
    }
}