package com.mufid.istory.ui.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.mufid.istory.R


class PasswordEditText : MyEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            @SuppressLint("StringFormatMatches")
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (text?.length in 1..5){
                    error = resources.getString(R.string.length_character, 6)
                }
            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
}