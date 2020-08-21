package com.wisnitech.inputamountcomponent.utils

import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.RelativeSizeSpan
import android.widget.EditText
import android.widget.TextView
import java.text.NumberFormat
import java.util.*

fun EditText.setCurrencyToCustomFormat(symbol: String? = "$", maxLength: Int, invisibleTextView: TextView, onEnd: (Double) -> Unit) {

    fun autosizeText(size: Float): Float {
        return size / (resources.displayMetrics.scaledDensity)
    }

    addTextChangedListener(object : TextWatcher {

        private var current = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable?) {
            textSize = autosizeText(invisibleTextView.textSize)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            if (!s.isNullOrEmpty() && s.toString() != current) {
                removeTextChangedListener(this)
                val cleanString = s.toString().replace("\\D".toRegex(), "")
                var parsed = if (cleanString.isBlank()) 0.0 else cleanString.toDouble()
                parsed /= 100
                val locale = Locale("es", "AR")
                val formated = NumberFormat.getCurrencyInstance(locale).format(parsed)

                if (formated.length <= maxLength) {
                    current = formated
                    onEnd(parsed)
                }

                val character = current[current.length - 3]
                val spannable = SpannableString(
                    current
                        .replace(" ", "")
                        .replace(character.toString(), " ")
                )

                spannable.apply {
                    val end = current.length
                    val defaultFractionDigits =
                        Currency.getInstance(locale).defaultFractionDigits + 1
                    val currencySymbolLength = 2
                    val ratio = 2.5
                    val proportion = 0.2

                    // $
                    setSpan(
                        RelativeSizeSpan(proportion.toFloat()),
                        0, currencySymbolLength,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    setSpan(
                        SuperscriptSpanAdjuster(ratio),
                        0, currencySymbolLength,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    // ,00
                    setSpan(
                        RelativeSizeSpan(proportion.toFloat()),
                        end - defaultFractionDigits, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    setSpan(
                        SuperscriptSpanAdjuster(ratio),
                        end - defaultFractionDigits, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                setText(spannable)
                setSelection(spannable.length)
                invisibleTextView.setText(spannable, TextView.BufferType.EDITABLE)
                addTextChangedListener(this)
            }
        }
    })
}