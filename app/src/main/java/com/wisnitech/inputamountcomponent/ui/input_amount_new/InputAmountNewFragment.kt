package com.wisnitech.inputamountcomponent.ui.input_amount_new

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wisnitech.inputamountcomponent.databinding.InputAmountNewFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class InputAmountNewFragment : Fragment() {

    private val vModel: InputAmountNewViewModel by viewModel()
    private lateinit var binding: InputAmountNewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InputAmountNewFragmentBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputAmountTest.setOnInputChangeListener {
            Log.d("flmwg","INPUT: $it")
        }
    }

    companion object {
        fun newInstance() = InputAmountNewFragment()
    }

}
