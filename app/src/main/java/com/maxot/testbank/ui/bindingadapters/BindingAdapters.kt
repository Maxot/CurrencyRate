package com.maxot.testbank.ui.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat


object BindingAdapters {
    @BindingAdapter("app:currency_rate")
    @JvmStatic
    fun setCurrencyRate(view: TextView, rate: Double) {

        try {
            val decimalFormat = DecimalFormat("#.####")
            val `val` = decimalFormat.format(rate)
            view.text = `val`
        } catch (exception: NumberFormatException) {
            // Do nothing
        }

    }

}