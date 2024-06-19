package com.mufid.istory.ui.customviews

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.mufid.istory.R

class StoryEditText: AppCompatEditText {

    private var textLength: Int = 0

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleSet: Int): super(context, attributeSet, defStyleSet) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, start: Int, p2: Int, after: Int) {
                textLength = start + after
                error = if (textLength < 6) resources.getString(R.string.text_error_passwd) else null
                if (textLength == 0) error = null
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}