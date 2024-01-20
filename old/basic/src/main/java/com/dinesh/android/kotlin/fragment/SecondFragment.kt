package com.dinesh.android.kotlin.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.dinesh.android.R
import com.dinesh.android.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {
    private val TAG = "log_" + SecondFragment::class.java.name.split(SecondFragment::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    lateinit var result: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView: ")

        passDataBetweenFragment()

        return binding.root
    }

    private fun passDataBetweenFragment() {
        binding.btnFirstFragment.setOnClickListener {
            result = binding.etSecondFragment.text.toString()
            Log.d(TAG, "btnFirstFragment: ${result}")
            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }

        binding.btnThirdFragment.setOnClickListener {
            result = binding.etSecondFragment.text.toString()
            Log.d(TAG, "btnThirdFragment: ${binding.etSecondFragment.text}")
            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment)
        }

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            result = bundle.getString("bundleKey").toString()
            Log.e(TAG, "setFragmentResultListener: ${result}")
            binding.etSecondFragment.setText(result)
            Toast.makeText(activity, result.toString(), Toast.LENGTH_SHORT).show()
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