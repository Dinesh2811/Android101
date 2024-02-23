package com.dinesh.android.basic.fragment

import android.os.Build
import android.view.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.activity.compose.*
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material.icons.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dinesh.android.databinding.FragmentMainBinding
import com.dinesh.basic.app.LogLevel
import com.dinesh.basic.app.log

private val TAG = "log_" + MainActivity::class.java.name.split(MainActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class MainActivity: AppCompatActivity() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = FragmentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        replaceFragment(BottomNavigationMenuFragment1())
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            if (shouldReplaceFragment) {
                when (menuItem.itemId) {
                    R.id.bottomNavigationMenu1 -> replaceFragment(BottomNavigationMenuFragment1())
                    R.id.bottomNavigationMenu2 -> replaceFragment(BottomNavigationMenuFragment2())
                    R.id.bottomNavigationMenu3 -> replaceFragment(BottomNavigationMenuFragment3())
                    R.id.bottomNavigationMenu4 -> replaceFragment(BottomNavigationMenuFragment4())
                }
            }
            true
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                LogLevel.Debug.log(TAG = TAG, message = "onBackPressed: ${supportFragmentManager.backStackEntryCount}")
//                supportFragmentManager.popBackStack()
                if (shouldPopBackStack()) {
                    supportFragmentManager.popBackStack()
                } else {

                }
            }
        }

        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.replaceMainFragment, fragment)
            .addToBackStack(null)
            .commit()

        if (fragment is BottomNavigationMenuFragment1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                window.decorView.systemUiVisibility = 0
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    private var shouldReplaceFragment = true
    fun navigateTo(fragment: Fragment, @IdRes menuItemId: Int, bundle: Bundle? = null) {
        shouldReplaceFragment = false
        if (bundle == null) {
            binding.bottomNavigationView.selectedItemId = menuItemId
            shouldReplaceFragment = true
            supportFragmentManager.beginTransaction().replace(R.id.replaceMainFragment, fragment).commit()
        } else {
            binding.bottomNavigationView.selectedItemId = menuItemId
            shouldReplaceFragment = true
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.replaceMainFragment, fragment).commit()
        }

        if (fragment is BottomNavigationMenuFragment1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                window.decorView.systemUiVisibility = 0
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    private fun shouldPopBackStack(): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.replaceMainFragment)
        return currentFragment !is BottomNavigationMenuFragment1 &&
                currentFragment !is BottomNavigationMenuFragment2 &&
                currentFragment !is BottomNavigationMenuFragment3 &&
                currentFragment !is BottomNavigationMenuFragment4
    }

}
