package com.wisnitech.inputamountcomponent.utils

import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class SuperscriptSpanAdjuster(ratio: Double) : MetricAffectingSpan() {
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