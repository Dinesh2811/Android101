package com.dinesh.android.app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.dinesh.android.MainActivity
import com.dinesh.android.root.RvMain
import com.dinesh.android.test.Testing
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial

private val TAG = "log_" + ThemePreference::class.java.name.split(ThemePreference::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

open class ThemePreference : AppCompatActivity() {
    open lateinit var parentLayout: FrameLayout
    lateinit var v: View
    lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentTheme = getSharedPreferences("sharedPreferences_$packageName", MODE_PRIVATE).getInt("Theme", R.style.Theme_Material3)
        setTheme(currentTheme)

        setContentViewLayout(R.layout.theme_settings_dialog)

        initializeView()
        setDefaultValue(currentTheme)

        init()
    }

    private fun onExit() {
//        removeFancySnackBar()
        removeParentLayout()
    }

    open fun setContentViewLayout(layout: Int) {
        setContentView(R.layout.toolbar_layout_v1)
        parentLayout = findViewById(R.id.framelayout_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        v = LayoutInflater.from(this).inflate(layout, parentLayout, false)
        parentLayout.addView(v)

//        toolbar.setNavigationOnClickListener {
//            parentLayout.removeAllViews()
//            onBackNavigationAction()
//        }

        val classNameAsString = getSharedPreferences("sharedPreferences_$packageName", MODE_PRIVATE).getString("classNameTesting", RvMain::class.java.name)
        if (saveLastClassNameList.size > 0) {
            if (this::class.java == saveLastClassNameList.first || MainActivity::class.java == saveLastClassNameList.first) {
                toolbar.navigationIcon = null
                if (classNameAsString != RvMain::class.java.name && this::class.java.name != RvMain::class.java.name){
                    toolbar.setNavigationIcon(R.drawable.baseline_home_24)
                }
            } else{
                toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
            }
        }

        toolbar.setNavigationOnClickListener {
            if (classNameAsString != RvMain::class.java.name){
                parentLayout.removeAllViews()
                startActivity(Intent(this, RvMain::class.java))
                finish()
            } else{
                parentLayout.removeAllViews()
                onBackNavigationAction()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_main, menu)
        val selectThemeItem = menu.findItem(R.id.action_select_theme)
        selectThemeItem.isVisible = false // Hide the menu item

        val actionTesting = menu.findItem(R.id.action_testing)
        actionTesting.isVisible = this::class.java != RvMain::class.java
        actionTesting.isChecked = getSharedPreferences("sharedPreferences_$packageName", MODE_PRIVATE).getString("classNameTesting", RvMain::class.java.name) == this::class.java.name

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_select_theme -> {
                launchActivity(ThemePreference::class.java)
                true
            }
            R.id.action_testing -> {
                if (item.isChecked){
                    item.isChecked = false
                    getSharedPreferences("sharedPreferences_$packageName", MODE_PRIVATE).edit().putString("classNameTesting", RvMain::class.java.name).apply()
                } else{
                    item.isChecked = true
                    getSharedPreferences("sharedPreferences_$packageName", MODE_PRIVATE).edit().putString("classNameTesting", this::class.java.name).apply()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
//        onExit()
    }

    override fun onDestroy() {
        super.onDestroy()
        onExit()
    }

    override fun onBackPressed() {
        onBackNavigationAction()
    }

    private fun setDefaultValue(theme: Int) {
        val themeData = themes[theme] ?: return

        checkRadioGroup(themeData.id)
        isDynamicThemeEnabled(themeData.isDynamicEnabled)
        isAmoledThemeEnabled(themeData.isAmoledEnabled)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            switchDynamic.isEnabled = true
        } else {
            isDynamicThemeEnabled(false)
        }
        when (theme) {
            R.style.Theme_Material3 -> {
                switchDynamic.isChecked = Build.VERSION.SDK_INT > Build.VERSION_CODES.R
                switchDynamic.isEnabled = false
            }
            R.style.Theme_Dynamic_PureBlack, R.style.Theme_Dynamic_PureBlack_V2, R.style.Theme_PureBlack, R.style.Theme_PureBlack_V2 -> switchAmoled.isEnabled = true
        }
    }

    private val themes = mapOf(
        R.style.Theme_Material3 to ThemeData(R.id.defaultRadioButton, isDynamicEnabled = false, isAmoledEnabled = false),
        R.style.Theme_Dynamic_Light to ThemeData(R.id.lightRadioButton, isDynamicEnabled = true, isAmoledEnabled = false),
        R.style.Theme_Dynamic_Dark to ThemeData(R.id.darkRadioButton, isDynamicEnabled = true, isAmoledEnabled = false),
        R.style.Theme_Dynamic_PureBlack to ThemeData(R.id.amoledRadioButton, isDynamicEnabled = true, isAmoledEnabled = false),
        R.style.Theme_Dynamic_PureBlack_V2 to ThemeData(R.id.amoledRadioButton, isDynamicEnabled = true, isAmoledEnabled = true),
        R.style.Theme_Light to ThemeData(R.id.lightRadioButton, isDynamicEnabled = false, isAmoledEnabled = false),
        R.style.Theme_Dark to ThemeData(R.id.darkRadioButton, isDynamicEnabled = false, isAmoledEnabled = false),
        R.style.Theme_PureBlack to ThemeData(R.id.amoledRadioButton, isDynamicEnabled = false, isAmoledEnabled = false),
        R.style.Theme_PureBlack_V2 to ThemeData(R.id.amoledRadioButton, isDynamicEnabled = false, isAmoledEnabled = true)
    )

    private fun init() {
        radioDefault.setOnClickListener {
            saveSharedPreference(R.style.Theme_Material3)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                switchDynamic.isChecked = true
            } else {
                switchDynamic.isChecked = false
            }
            switchDynamic.isEnabled = false
            isAmoledThemeEnabled(false)
        }

        radioLight.setOnClickListener {
            if (switchDynamic.isChecked) {
                saveSharedPreference(R.style.Theme_Dynamic_Light)
                switchDynamic.isChecked = true
            } else {
                saveSharedPreference(R.style.Theme_Light)
                switchDynamic.isChecked = false
            }
            isAmoledThemeEnabled(false)
        }

        radioDark.setOnClickListener {
            if (switchDynamic.isChecked) {
                saveSharedPreference(R.style.Theme_Dynamic_Dark)
                switchDynamic.isChecked = true
            } else {
                saveSharedPreference(R.style.Theme_Dark)
                switchDynamic.isChecked = false
            }
            isAmoledThemeEnabled(false)
        }

        radioAmoled.setOnClickListener {
            if (((switchDynamic.isChecked)) && ((switchAmoled.isChecked))) {
                saveSharedPreference(R.style.Theme_Dynamic_PureBlack_V2)
                switchDynamic.isChecked = true
            } else if ((!(switchDynamic.isChecked)) && ((switchAmoled.isChecked))) {
                saveSharedPreference(R.style.Theme_PureBlack_V2)
                switchDynamic.isChecked = false
            } else if (((switchDynamic.isChecked)) && (!(switchAmoled.isChecked))) {
                saveSharedPreference(R.style.Theme_Dynamic_PureBlack)
                switchDynamic.isChecked = true
            } else if ((!(switchDynamic.isChecked)) && (!(switchAmoled.isChecked))) {
                saveSharedPreference(R.style.Theme_PureBlack)
                switchDynamic.isChecked = false
            }
        }

        switchDynamic.setOnClickListener {
            switchAmoled.isEnabled = false
            if (switchDynamic.isChecked) {
                when (radioGroup.checkedRadioButtonId) {
                    R.id.defaultRadioButton -> {
                        saveSharedPreference(R.style.Theme_Material3)
                        switchDynamic.isChecked = Build.VERSION.SDK_INT > Build.VERSION_CODES.R
                        switchDynamic.isEnabled = false
                    }
                    R.id.lightRadioButton -> saveSharedPreference(R.style.Theme_Dynamic_Light)
                    R.id.darkRadioButton -> saveSharedPreference(R.style.Theme_Dynamic_Dark)
                    R.id.amoledRadioButton -> {
                        switchAmoled.isEnabled = true
                        if (switchAmoled.isChecked) {
                            saveSharedPreference(R.style.Theme_Dynamic_PureBlack_V2)
                        } else {
                            saveSharedPreference(R.style.Theme_Dynamic_PureBlack)
                        }
                    }
                }
            } else {
                when (radioGroup.checkedRadioButtonId) {
                    R.id.defaultRadioButton -> {
                        saveSharedPreference(R.style.Theme_Material3)
                        switchDynamic.isChecked = Build.VERSION.SDK_INT > Build.VERSION_CODES.R
                        switchDynamic.isEnabled = false
                    }
                    R.id.lightRadioButton -> saveSharedPreference(R.style.Theme_Light)
                    R.id.darkRadioButton -> saveSharedPreference(R.style.Theme_Dark)
                    R.id.amoledRadioButton -> {
                        switchAmoled.isEnabled = true
                        if (switchAmoled.isChecked) {
                            saveSharedPreference(R.style.Theme_PureBlack_V2)
                        } else {
                            saveSharedPreference(R.style.Theme_PureBlack)
                        }
                    }
                }
            }
        }

        switchAmoled.setOnClickListener {
            if (switchDynamic.isChecked) {
                when (radioGroup.checkedRadioButtonId) {
                    R.id.defaultRadioButton -> saveSharedPreference(R.style.Theme_Material3)
                    R.id.lightRadioButton -> saveSharedPreference(R.style.Theme_Dynamic_Light)
                    R.id.darkRadioButton -> saveSharedPreference(R.style.Theme_Dynamic_Dark)
                    R.id.amoledRadioButton -> {
                        if (switchAmoled.isChecked) {
                            saveSharedPreference(R.style.Theme_Dynamic_PureBlack_V2)
                        } else {
                            saveSharedPreference(R.style.Theme_Dynamic_PureBlack)
                        }
                    }
                }
            } else {
                when (radioGroup.checkedRadioButtonId) {
                    R.id.defaultRadioButton -> saveSharedPreference(R.style.Theme_Material3)
                    R.id.lightRadioButton -> saveSharedPreference(R.style.Theme_Light)
                    R.id.darkRadioButton -> saveSharedPreference(R.style.Theme_Dark)
                    R.id.amoledRadioButton -> {
                        if (switchAmoled.isChecked) {
                            saveSharedPreference(R.style.Theme_PureBlack_V2)
                        } else {
                            saveSharedPreference(R.style.Theme_PureBlack)
                        }
                    }
                }
            }
        }
    }

    private fun saveSharedPreference(theme: Int) {
        val editor = getSharedPreferences("sharedPreferences_$packageName", MODE_PRIVATE).edit()
        editor.putInt("Theme", theme)
        editor.apply()
        recreate()
    }

    private fun checkRadioGroup(radioButtonID: Int) {
        radioGroup.check(radioButtonID)
    }

    private fun isDynamicThemeEnabled(booleanValue: Boolean) {
        switchDynamic.isEnabled = booleanValue
        switchDynamic.isChecked = booleanValue
    }

    private fun isAmoledThemeEnabled(booleanValue: Boolean) {
        switchAmoled.isEnabled = booleanValue
        switchAmoled.isChecked = booleanValue
    }

    private fun removeParentLayout() {
        parentLayout.removeView(v)
        parentLayout.removeAllViews()
    }

    open fun <T : View> findView(id: Int): T {
        return v.findViewById<T>(id)
    }

    /**
     * Initializes the views in the layout.
     */
    // TODO: Theme
    private fun initializeView() {
        radioGroup = v.findViewById<RadioGroup>(R.id.radioGroup)
        switchDynamic = v.findViewById(R.id.switch_dynamic_theme)
        switchAmoled = v.findViewById(R.id.switch_amoled_theme)
        radioDefault = v.findViewById<RadioButton>(R.id.defaultRadioButton)
        radioLight = v.findViewById<RadioButton>(R.id.lightRadioButton)
        radioDark = v.findViewById<RadioButton>(R.id.darkRadioButton)
        radioAmoled = v.findViewById<RadioButton>(R.id.amoledRadioButton)
    }

    lateinit var radioGroup: RadioGroup
    lateinit var switchDynamic: SwitchMaterial
    lateinit var switchAmoled: SwitchMaterial
    lateinit var radioDefault: RadioButton
    lateinit var radioLight: RadioButton
    lateinit var radioDark: RadioButton
    lateinit var radioAmoled: RadioButton
}

data class ThemeData(
    val id: Int,
    val isDynamicEnabled: Boolean,
    val isAmoledEnabled: Boolean
)

