package com.example.minigolf.ui

import android.app.AlertDialog
import android.content.Context
import com.example.minigolf.R

object InstructionsDialog {
    fun show(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.instructions_title))
            .setMessage(context.getString(R.string.instructions_text))
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}
