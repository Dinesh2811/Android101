package com.dinesh.android.basic.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dinesh.android.R
import com.dinesh.android.databinding.BottomNavigationMenuFragment1Binding
import com.dinesh.basic.app.LogLevel
import com.dinesh.basic.app.log
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


class BottomNavigationMenuFragment2: Fragment() {
    private val TAG = "log_" + BottomNavigationMenuFragment2::class.java.name.split(BottomNavigationMenuFragment2::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private var _binding: BottomNavigationMenuFragment1Binding? = null
    private var shouldPopBackStack: Boolean = false
    private var openTab: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomNavigationMenuFragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            it.window?.statusBarColor = ContextCompat.getColor(it, R.color.white)
            it.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        openTab = arguments?.getString("Tab", "")?: ""
        Log.i("log_info", "onViewCreated: openTab --> ${openTab}")
        addTab(view)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }

        shouldPopBackStack = arguments?.getBoolean("shouldPopBackStack", false)?: false
        if (shouldPopBackStack) {
            toolbarBack?.visibility = View.VISIBLE
            toolbarTitle?.gravity = Gravity.LEFT
        } else {
            toolbarBack?.visibility = View.GONE
            toolbarTitle?.gravity = Gravity.LEFT
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                shouldPopBackStack = arguments?.getBoolean("shouldPopBackStack", false)?: false
                LogLevel.Debug.log(TAG = TAG, message = "onBackPressed: shouldPopBackStack --> ${shouldPopBackStack}")
                if (shouldPopBackStack) {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

//    /*

    private fun addTab(view: View) {
        toolbarTitle = view.findViewById(R.id.toolbarTitle)
        toolbarBack = view.findViewById(R.id.back)

        // Add tabs
        val adapter = TabAdapter(requireActivity())
        adapter.addFragment(TabFragment1(), "Tab1", com.dinesh.xml.R.drawable.baseline_home_24)
        adapter.addFragment(TabFragment2(), "Tab2", com.dinesh.xml.R.drawable.baseline_home_24)

        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        binding.tabLayout.tabMode = TabLayout.TEXT_ALIGNMENT_GRAVITY

        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = adapter.getTabTitle(position)
            //            tab.setIcon(adapter.getTabIcon(position))
        }.attach()

        toolbarBack?.setOnClickListener {
            shouldPopBackStack = arguments?.getBoolean("shouldPopBackStack", false)?: false
            if (shouldPopBackStack) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

    }

//     */

    private val binding get() = _binding!!
    private var toolbarTitle: TextView? = null
    private var toolbarBack: ImageView? = null
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
