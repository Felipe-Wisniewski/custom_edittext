package com.wisnitech.inputamountcomponent.di

import com.wisnitech.inputamountcomponent.ui.input_amount_new.InputAmountNewViewModel
import com.wisnitech.inputamountcomponent.ui.input_amount_old.InputAmountOldViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { InputAmountOldViewModel() }
    viewModel { InputAmountNewViewModel() }
}