package com.dinesh.android.kotlin.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import com.dinesh.android.databinding.FragmentMainKotlinBinding

private val TAG = "log_" + Main::class.java.name.split(Main::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class Main : AppCompatActivity() {
    private lateinit var binding: FragmentMainKotlinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = FragmentMainKotlinBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        setContentView(R.layout.fragment_main_kotlin)

    }


}