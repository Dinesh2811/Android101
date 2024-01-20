package com.dinesh.android.kotlin.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController


import com.dinesh.android.R
import com.dinesh.android.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {
    private val TAG = "log_" + FirstFragment::class.java.name.split(FirstFragment::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    lateinit var result: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView: ")

        passDataBetweenFragment()

        return binding.root
    }

    private fun passDataBetweenFragment() {
        binding.btnSecondFragment.setOnClickListener {
            result = binding.etFirstFragment.text.toString()
            Log.d(TAG, "btnSecondFragment: ${result}")
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }

        binding.btnThirdFragment.setOnClickListener {
            result = binding.etFirstFragment.text.toString()
            Log.d(TAG, "btnThirdFragment: ${result}")
            findNavController().navigate(R.id.action_firstFragment_to_thirdFragment)
        }

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            result = bundle.getString("bundleKey").toString()
            Log.e(TAG, "setFragmentResultListener: ${result}")
            binding.etFirstFragment.setText(result)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG, "onDestroyView: ")

        //  passDataBetweenFragment
        setFragmentResult("requestKey", bundleOf("bundleKey" to result))

        _binding = null
    }

}