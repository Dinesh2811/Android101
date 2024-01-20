package com.dinesh.android.app

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.dinesh.android.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView


private val TAG = "log_" + BaseActivity::class.java.name.split(BaseActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

open class BaseActivity : ToolbarMain() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        getFancySnackBar().removeFancySnackBar()
//        getFancyDialog().removeFancyDialog()
    }

    private var fancySnackBarInstance: FancySnackBar? = null
    open fun getFancySnackBar(fancySnackbarLayout: Int = R.layout.fancy_snackbar_layout): FancySnackBar {
        if (fancySnackBarInstance == null) {
            fancySnackBarInstance = FancySnackBar()
        }
//        fancySnackBarInstance!!.fancySnackbar(fancySnackbarLayout)
        return fancySnackBarInstance!!
    }

    /**
     * Initializes FancySnackBar.
     */
    // TODO: FancySnackBar
    inner class FancySnackBar {
        private var snackbar_btn1: MaterialButton? = null
        private var snackbar_btn2: MaterialButton? = null
        private var snackbar_btn3: MaterialButton? = null
        private var duration: Long? = null

        fun fancySnackbar(fancySnackbarLayout: Int = R.layout.fancy_snackbar_layout): FancySnackBar {
            frameLayoutSnackbar = findViewById(R.id.framelayout_snackbar)
            val layoutParams = frameLayoutSnackbar?.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.verticalBias = 1.0f
            frameLayoutSnackbar?.layoutParams = layoutParams
            viewFancySnackBar = LayoutInflater.from(this@BaseActivity).inflate(fancySnackbarLayout, frameLayoutSnackbar, false)
            snackbar_btn1 = viewFancySnackBar?.findViewById(R.id.snackbar_btn1)
            snackbar_btn2 = viewFancySnackBar?.findViewById(R.id.snackbar_btn2)
            snackbar_btn3 = viewFancySnackBar?.findViewById(R.id.snackbar_btn3)

            snackbar_btn1?.visibility = View.GONE
            snackbar_btn2?.visibility = View.GONE
            snackbar_btn3?.visibility = View.GONE
            return this
        }

        fun btn1(
            message: String, duration: Int? = null, icon: Int? = null, iconGravity: Int = MaterialButton.ICON_GRAVITY_END, iconPadding: Int = 8,
            btnClickListener: View.OnClickListener? = null
        ): FancySnackBar {
            snackbar_btn1?.visibility = View.VISIBLE
            if (duration != null) {
                this.duration = (1000 * duration).toLong()
            }
            snackbar_btn1?.text = message
            if (icon != null) {
                snackbar_btn1?.setIconResource(icon)
                snackbar_btn1?.iconGravity = iconGravity
                val iconPaddingPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, iconPadding.toFloat(), resources.displayMetrics
                ).toInt()
                snackbar_btn1?.iconPadding = iconPaddingPx
            } else {
                snackbar_btn1?.icon = null
            }
            snackbar_btn1?.setOnClickListener(btnClickListener)
            return this
        }

        fun btn2(
            icon: Int? = null, message: String? = null, duration: Int? = null, iconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START, iconPadding: Int = 0,
            btnClickListener: View.OnClickListener? = null
        ): FancySnackBar {
            snackbar_btn2?.visibility = View.VISIBLE
            if (duration != null) {
                this.duration = (1000 * duration).toLong()
            }
            if (icon != null) {
                snackbar_btn2?.setIconResource(icon)
                snackbar_btn2?.iconGravity = iconGravity
                val iconPaddingPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, iconPadding.toFloat(), resources.displayMetrics
                ).toInt()
                snackbar_btn2?.iconPadding = iconPaddingPx
            } else {
                snackbar_btn2?.icon = null
            }
            if (message != null) {
                snackbar_btn2?.text = message
                snackbar_btn2?.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    matchConstraintMaxWidth = ConstraintLayout.LayoutParams.WRAP_CONTENT
                }
            } else {
                if (icon == null) {
                    snackbar_btn2?.setIconResource(R.drawable.baseline_close_24)
                }
            }
            snackbar_btn2?.setOnClickListener(btnClickListener)
            return this
        }

        fun btn3(
            icon: Int? = null, message: String? = null, duration: Int? = null, iconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START, iconPadding: Int = 0,
            btnClickListener: View.OnClickListener? = null
        ): FancySnackBar {
            snackbar_btn3?.visibility = View.VISIBLE
            if (duration != null) {
                this.duration = (1000 * duration).toLong()
            }
            if (icon != null) {
                snackbar_btn3?.setIconResource(icon)
                snackbar_btn3?.iconGravity = iconGravity
                val iconPaddingPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, iconPadding.toFloat(), resources.displayMetrics
                ).toInt()
                snackbar_btn3?.iconPadding = iconPaddingPx
            } else {
                snackbar_btn3?.icon = null
            }
            if (message != null) {
                snackbar_btn3?.text = message
                snackbar_btn3?.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    matchConstraintMaxWidth = ConstraintLayout.LayoutParams.WRAP_CONTENT
                }
            } else {
                if (icon == null) {
                    snackbar_btn3?.setIconResource(R.drawable.baseline_share_24)
                }
            }
            snackbar_btn3?.setOnClickListener(btnClickListener)
            return this
        }

        fun show(): FancySnackBar {
            if (snackbar_btn1?.visibility != View.GONE) {
                frameLayoutSnackbar?.addView(viewFancySnackBar)
                if (duration != null) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        frameLayoutSnackbar?.removeView(viewFancySnackBar)
                    }, duration!!)
                }
            }
            return this@FancySnackBar
        }

        fun dismiss(): FancySnackBar {
            removeFancySnackBar()
            return this
        }
        private var frameLayoutSnackbar: FrameLayout? = null
        private var viewFancySnackBar: View? = null

        internal fun removeFancySnackBar() {
            frameLayoutSnackbar?.removeView(viewFancySnackBar)
            frameLayoutSnackbar?.removeAllViews()
            viewFancySnackBar = null
        }

    }



    private var fancyDialogInstance: FancyDialog? = null
    open fun getFancyDialog(fancyDialogLayout: Int = R.layout.fancy_dialog_layout): FancyDialog {
        if (fancyDialogInstance == null) {
            fancyDialogInstance = FancyDialog()
        }
        fancyDialogInstance!!.fancyDialog(fancyDialogLayout)
        return fancyDialogInstance!!
    }

    inner class FancyDialog {
        private var frameLayoutMain: FrameLayout? = null
        private var rootDialogLayout: MaterialCardView? = null
        private var tvTitle: MaterialTextView? = null
        private var tvTop: MaterialTextView? = null
        private var tvMiddle: MaterialTextView? = null
        private var tvBottom: MaterialTextView? = null
        private var btn1: MaterialButton? = null
        private var btn2: MaterialButton? = null
        private var btn3: MaterialButton? = null
        private var cancelable = true

        fun fancyDialog(fancyDialogLayout: Int = R.layout.fancy_dialog_layout): FancyDialog {
            frameLayoutDialog = findViewById(R.id.framelayout_dialog)
            val layoutParams = frameLayoutDialog?.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.verticalBias = 0.5f
            frameLayoutDialog?.layoutParams = layoutParams
            viewFancyDialog = LayoutInflater.from(this@BaseActivity).inflate(fancyDialogLayout, frameLayoutDialog, false)
            frameLayoutMain = findViewById(R.id.framelayout_main)
            rootDialogLayout = viewFancyDialog?.findViewById(R.id.root_dialog_layout)
            tvTitle = viewFancyDialog?.findViewById(R.id.dialog_tv_title)
            tvTop = viewFancyDialog?.findViewById(R.id.dialog_tv_top)
            tvMiddle = viewFancyDialog?.findViewById(R.id.dialog_tv_middle)
            tvBottom = viewFancyDialog?.findViewById(R.id.dialog_tv_bottom)
            btn1 = viewFancyDialog?.findViewById(R.id.dialog_btn1)
            btn2 = viewFancyDialog?.findViewById(R.id.dialog_btn2)
            btn3 = viewFancyDialog?.findViewById(R.id.dialog_btn3)

            rootDialogLayout?.visibility = View.GONE
            tvTitle?.visibility = View.GONE
            tvTop?.visibility = View.GONE
            tvMiddle?.visibility = View.GONE
            tvBottom?.visibility = View.GONE
            btn1?.visibility = View.GONE
            btn2?.visibility = View.GONE
            btn3?.visibility = View.GONE

            return this
        }

        fun setTitle(title: String, btnClickListener: View.OnClickListener? = null): FancyDialog{
            tvTitle?.visibility = View.VISIBLE
            btn1?.visibility = View.VISIBLE
            tvTitle?.text = title
            return this
        }

        fun setTopMessage(topMessage: String): FancyDialog{
            tvTop?.visibility = View.VISIBLE
            btn1?.visibility = View.VISIBLE
            tvTop?.text = topMessage
            return this
        }

        fun setMiddleMessage(middleMessage: String): FancyDialog{
            tvMiddle?.visibility = View.VISIBLE
            btn1?.visibility = View.VISIBLE
            tvMiddle?.text = middleMessage
            return this
        }

        fun setMiddleMessage(permanentlyDeniedPermissionList: ArrayList<String>): FancyDialog{
            tvMiddle?.visibility = View.VISIBLE
            btn1?.visibility = View.VISIBLE

            val listOfPermission = ArrayList<String>()
            for (permission in permanentlyDeniedPermissionList) {
                listOfPermission.add(permission.substringAfter("android.permission.").split(" ").joinToString(" ") { it })
            }

            val spannableStringBuilder = SpannableStringBuilder()
            listOfPermission.forEach { item ->
                spannableStringBuilder.append("• $item\n")      //  "\u2022 "
            }

            tvMiddle?.text = spannableStringBuilder
            return this
        }

        fun setBottomMessage(bottomMessage: String): FancyDialog{
            tvBottom?.visibility = View.VISIBLE
            btn1?.visibility = View.VISIBLE

            tvBottom?.text = bottomMessage
            return this
        }

        fun setBtn1(text: String, clickListener: View.OnClickListener? = null): FancyDialog{
            btn1?.visibility = View.VISIBLE
            btn1?.text = text
            if (clickListener == null){
                btn1?.setOnClickListener { removeFancyDialog() }
            } else{
                btn1?.setOnClickListener(clickListener)
            }
            return this
        }

        fun setBtn2(text: String, clickListener: View.OnClickListener? = null): FancyDialog{
            btn2?.visibility = View.VISIBLE
            btn2?.text = text
            if (clickListener == null){
                btn2?.setOnClickListener { removeFancyDialog() }
            } else{
                btn2?.setOnClickListener(clickListener)
            }
            return this
        }

        fun setBtn3(text: String, clickListener: View.OnClickListener? = null): FancyDialog{
            btn3?.visibility = View.VISIBLE
            btn3?.text = text
            if (clickListener == null){
                btn3?.setOnClickListener { removeFancyDialog() }
            } else{
                btn3?.setOnClickListener(clickListener)
            }
            return this
        }

        fun setCancelable(cancelableBoolean: Boolean = true): FancyDialog{
            cancelable = cancelableBoolean
            if (cancelableBoolean){
                frameLayoutMain?.isClickable = true
                frameLayoutMain?.setOnClickListener { dismiss() }
            } else{
                frameLayoutMain?.isClickable = false
            }
            return this
        }

        fun show(): FancyDialog{
            rootDialogLayout?.visibility = View.VISIBLE
            frameLayoutDialog?.addView(viewFancyDialog)
            if (cancelable){
                frameLayoutMain?.isClickable = true
                frameLayoutMain?.setOnClickListener { dismiss() }
            } else{
                frameLayoutMain?.isClickable = false
            }
            frameLayoutMain?.isClickable = false

            return this
        }

        fun dismiss(): FancyDialog{
            frameLayoutMain?.isClickable = true
            removeFancyDialog()
            return this
        }

        private var frameLayoutDialog: FrameLayout? = null
        private var viewFancyDialog: View? = null
        internal fun removeFancyDialog() {
            frameLayoutDialog?.removeView(viewFancyDialog)
            frameLayoutDialog?.removeAllViews()
            viewFancyDialog = null
        }
    }





    private fun base() {
        Log.e(
            TAG, "BaseActivity: ${ThemePreference::class.java} --> ${ToolbarMain::class.java} --> ${FancySnackBar::class.java} --> " +
                    "${BaseActivity::class.java} --> "
        )
    }
}

/**
 * FancySnackBar()
 */
/*
        getFancySnackBar()
            .btn1("testing_layout")
            .btn2(btnClickListener = { getFancySnackBar().dismiss() })
            .show()
 */

/**
 * FancyDialog()
 */
/*
        getFancyDialog().setTitle("Permission needed")
            .setCancelable(false)
            .setTopMessage("To use this service please grant below permission:\n")
            .setMiddleMessage(permanentlyDeniedPermissionList)
            .setBottomMessage("Please grant the permission from Settings.")
            .setBtn1("Settings") {
                Toast.makeText(context, "Navigating to settings", Toast.LENGTH_SHORT).show()
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
                getFancyDialog().dismiss()
            }.setBtn2("Cancel") {
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
                getFancyDialog().dismiss()
            }.setBtn3("Exit"){
                Toast.makeText(context, "Exit the app", Toast.LENGTH_SHORT).show()
                getFancyDialog().dismiss()
                finishAffinity()
            }.show()

 */