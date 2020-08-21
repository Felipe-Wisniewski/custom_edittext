package com.wisnitech.inputamountcomponent.ui.input_amount_old

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InputAmountOldViewModel : ViewModel() {

    private val _doubleBalance: MutableLiveData<Double> = MutableLiveData()
    val doubleBalance: LiveData<Double> = _doubleBalance

    private val _transferValue: MutableLiveData<Double> = MutableLiveData()
    val transferValue: LiveData<Double> = _transferValue

    /*private val _etClick = MutableLiveData<Event<Unit>>()
    val etClick: LiveData<Event<Unit>> = _etClick*/

    init {
        _doubleBalance.value = 98.76
    }

    fun setTransferValue(value: Double) {
        _transferValue.value = value
    }

    fun etClick() {
//        _etClick.value = Event(Unit)
        Log.d("flmwg","etClick")
    }
}