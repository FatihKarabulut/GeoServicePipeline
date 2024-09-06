package com.ahbap.android.app.wikiclient.converter

import androidx.databinding.InverseMethod

object MaxRowsConverter {
    @InverseMethod("toStr")
    fun toInt(str : String) : Int = try {
        str.toInt()
    }
    catch (ex: NumberFormatException)
    {
        0
    }

    fun toStr(str : Int) : String = str.toString()
}