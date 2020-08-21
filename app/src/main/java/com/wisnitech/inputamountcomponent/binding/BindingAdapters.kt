package com.wisnitech.inputamountcomponent.binding

import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.BindingAdapter
import com.wisnitech.inputamountcomponent.R

@BindingAdapter(value = ["transferValue", "doubleBalance"])
fun backgroundCurrentBalance(view: View, transferValue: Double, doubleBalance: Double) {
    if (transferValue <= doubleBalance) {
        (view as EditText).setTextColor(getColor(view.context, R.color.black02))
    } else {
        (view as EditText).setTextColor(getColor(view.context, R.color.secondary01Dark))
    }
}