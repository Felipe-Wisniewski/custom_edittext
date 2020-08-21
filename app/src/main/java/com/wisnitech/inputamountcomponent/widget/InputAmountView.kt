package com.wisnitech.inputamountcomponent.widget

import android.content.Context
import android.text.*
import android.text.style.MetricAffectingSpan
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.wisnitech.inputamountcomponent.R
import kotlinx.android.synthetic.main.input_amount_view.view.*
import java.text.NumberFormat
import java.util.*

class InputAmountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    style: Int = 0
) : ConstraintLayout(context, attrs, style) {

    var showDecimalNumbers = true
    var currencySymbol = "$"
    var inputListener: Double? = null

    init {
        View.inflate(context, R.layout.input_amount_view, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.InputAmountView)
        showDecimalNumbers = attributes.getBoolean(R.styleable.InputAmountView_showDecimalNumber, true)
        currencySymbol = attributes.getString(R.styleable.InputAmountView_currencySymbol).toString()
        attributes.recycle()

        currency_symbol.text = currencySymbol
        setupInputAmountText()
    }

    private fun setupInputAmountText() {
        txt_invisible_amount.setText("0", TextView.BufferType.EDITABLE)

        edt_input_amount.apply {
            textSize = autoSizeText(this@InputAmountView.txt_invisible_amount.textSize)

            addTextChangedListener(object : TextWatcher {

                private var current = ""

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if (!s.isNullOrEmpty() && s.toString() != current) {

                        removeTextChangedListener(this)

                        Log.d("flmwg","- - - - - - - - - - - - - - - - - - - - - - - - -")
                        Log.d("flmwg","string: $s")
                        val cleanString = s.toString().replace("\\D".toRegex(), "")
                        Log.d("flmwg","cleanString: $cleanString")

                        var parsed = if (cleanString.isBlank()) 0.0 else cleanString.toDouble()
                        Log.d("flmwg","parsed: $parsed")
                        parsed /= 100
                        Log.d("flmwg","parsed 100/inputListener: $parsed")

                        val locale = Locale("es", "AR")

                        val formated = NumberFormat.getCurrencyInstance(locale).format(parsed)
                        Log.d("flmwg","formated/current: $formated")

                        if (formated.length <= 13) {      //maxLength=13
                            current = formated
                            inputListener = parsed
                        }

                        val character = current[current.length - 3]
                        Log.d("flmwg","character: $character")

                        val spannable = SpannableString(current
                                .replace(" ", "")
                                .replace(character.toString(), " "))

                        Log.d("flmwg","spannable: $spannable")

                        spannable.apply {

                            val end = current.length
                            Log.d("flmwg","spannable end/current.length: $end")

                            val defaultFractionDigits = Currency.getInstance(locale).defaultFractionDigits + 1
                            Log.d("flmwg","spannable defaultFractionDigits: $defaultFractionDigits")

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
                        Log.d("flmwg","spannable setText: $spannable")
                        setSelection(spannable.length)
                        Log.d("flmwg","spannable setSelection: ${spannable.length}")

                        this@InputAmountView.txt_invisible_amount.setText(spannable, TextView.BufferType.EDITABLE)

                        addTextChangedListener(this)
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    textSize = autoSizeText(this@InputAmountView.txt_invisible_amount.textSize)
                }
            })
            setText(0.toString())
        }
    }

    private fun autoSizeText(size: Float): Float {
        return size / resources.displayMetrics.density + 0.2f
    }

    fun setOnInputChangeListener(callback: (input: Double?) -> Unit) {
        callback(inputListener)
    }

    inner class SuperscriptSpanAdjuster(ratio: Double) : MetricAffectingSpan() {
        private var ratio = 0.5
        override fun updateDrawState(paint: TextPaint) {
            paint.baselineShift += (paint.ascent() * ratio).toInt()
        }

        override fun updateMeasureState(paint: TextPaint) {
            paint.baselineShift += (paint.ascent() * ratio).toInt()
        }

        init {
            this.ratio = ratio
        }
    }


}