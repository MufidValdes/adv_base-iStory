package com.mufid.istory.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    @SuppressLint("SimpleDateFormat")
    fun String.toAnotherDate(): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        var newDate: Date? = null

        try {
            newDate = dateFormat.parse(this)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return if (newDate != null) {
            SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(newDate)
        } else null
    }
}