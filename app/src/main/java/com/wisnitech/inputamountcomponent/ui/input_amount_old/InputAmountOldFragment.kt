package com.wisnitech.inputamountcomponent.ui.input_amount_old

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wisnitech.inputamountcomponent.R
import com.wisnitech.inputamountcomponent.databinding.InpuntAmountOldFragmentBinding
import com.wisnitech.inputamountcomponent.utils.setCurrencyToCustomFormat
import kotlinx.android.synthetic.main.inpunt_amount_old_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class InputAmountOldFragment : Fragment() {

    private val viewModel: InputAmountOldViewModel by viewModel()
    private lateinit var binding: InpuntAmountOldFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InpuntAmountOldFragmentBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@InputAmountOldFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        tvValue.setText("a", TextView.BufferType.EDITABLE) //calculate the right size for one character

        etValue.apply {
            textSize = autosizeText(tvValue.textSize)

            setCurrencyToCustomFormat("$", 16, tvValue) {
                viewModel.setTransferValue(it)
            }

            setText(0.toString())
        }
    }

    private fun autosizeText(size: Float): Float {
        return size / resources.displayMetrics.density + 0.2f
    }

    override fun onResume() {
        super.onResume()
        etValue.requestFocus()
//        etValue.showKeyboard()
    }

    companion object {
        fun newInstance() = InputAmountOldFragment()
    }
}