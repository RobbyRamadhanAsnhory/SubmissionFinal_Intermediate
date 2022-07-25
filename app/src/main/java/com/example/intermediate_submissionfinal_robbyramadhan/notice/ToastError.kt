package com.example.intermediate_submissionfinal_robbyramadhan.notice

import android.content.Context
import android.widget.Toast

object ToastError {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}