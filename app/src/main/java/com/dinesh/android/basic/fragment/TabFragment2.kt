package com.dinesh.android.basic.fragment

import android.view.View
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dinesh.android.R
import com.dinesh.android.databinding.TabFragment1Binding
import kotlinx.coroutines.launch

class TabFragment2: Fragment(R.layout.tab_fragment1) {
    private val TAG = "log_" + TabFragment2::class.java.name.split(TabFragment2::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1].takeLast(31)
    
    private var _binding: TabFragment1Binding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = TabFragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
            }
        }

        binding.textView.text = "TabFragment2"

    }

    private val binding get() = _binding!!
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


