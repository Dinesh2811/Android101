package com.dinesh.android.test

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.dinesh.android.R
import com.dinesh.android.app.BaseActivity
import com.dinesh.android.app.RequestPermission
import com.dinesh.android.rv.kotlin.nested.v2.RvChildModel
import com.dinesh.android.rv.kotlin.nested.v2.RvParentModel

private val TAG = "log_" + Testing::class.java.name.split(Testing::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class Testing : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.root_rv_list)

        fancySnackBar()
        fancyDialog()

    }

    private fun fancyDialog() {
        getFancyDialog().setTitle("Permission needed")
            .setCancelable(false)
            .setTopMessage("To use this service please grant below permission:\n")
            .setMiddleMessage("permanentlyDeniedPermissionList")
            .setBottomMessage("Please grant the permission from Settings.")
            .setBtn1("Settings") {
                Toast.makeText(this, "Navigating to settings", Toast.LENGTH_SHORT).show()
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
                getFancyDialog().dismiss()
            }.setBtn2("Cancel") {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
                getFancyDialog().dismiss()
            }.setBtn3("Exit") {
                Toast.makeText(this, "Exit the app", Toast.LENGTH_SHORT).show()
                getFancyDialog().dismiss()
                finishAffinity()
            }.show()
    }

    private fun fancySnackBar() {
        FancySnackBar().fancySnackbar(R.layout.fancy_snackbar_layout)
            .btn1("testing_layout")
            .btn2(btnClickListener = { getFancySnackBar().dismiss() })
            .show()
    }
}

/*

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


 */