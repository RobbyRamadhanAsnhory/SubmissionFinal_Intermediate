package com.example.intermediate_submissionfinal_robbyramadhan.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.intermediate_submissionfinal_robbyramadhan.R

class EmailEditText : AppCompatEditText {

    private var emailRegex: Regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    error = context.getString(R.string.err_empty_email)
                    requestFocus()
                } else if (!s.toString().matches(emailRegex)) {
                    error = context.getString(R.string.err_invalid_email)
                    requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}