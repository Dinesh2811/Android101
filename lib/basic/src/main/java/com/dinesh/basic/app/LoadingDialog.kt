package com.dinesh.basic.app

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.dinesh.xml.R
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * # Usage
 *
 *     @Inject lateinit var loadingDialog: LoadingDialog
 *         loadingDialog.startLoadingDialog()
 */

@ActivityScoped
class LoadingDialog @Inject constructor(@ActivityContext private val context: Context) {
    private var dialog: Dialog? = null
    private var textView: TextView? = null
    private var progressBar: ProgressBar? = null
    private var ivSuccess: ImageView? = null
    private var ivError: ImageView? = null

    private var currentJob: Job? = null

    fun startLoadingDialog() {
//        if (dialog?.isShowing == true) {
//            return
//        }
        cancelCurrentJob()
        dialog = Dialog(context)

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_loading, null)
        textView = view.findViewById(R.id.textLoading)
        progressBar = view.findViewById(R.id.progressBar)
        ivSuccess = view.findViewById(R.id.ivSuccess)
        ivError = view.findViewById(R.id.ivError)

        dialog?.setContentView(view)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        progressBar?.visibility = View.VISIBLE
        ivSuccess?.visibility = View.GONE
        ivError?.visibility = View.GONE

        dialog?.setOnCancelListener {
            cancelCurrentJob()
            dismissDialog()
        }

        dialog?.show()
    }

    suspend fun setLoadingText(text: String) {
        cancelCurrentJob()
        dismissDialog()
        startLoadingDialog()
        textView?.text = text
        progressBar?.visibility = View.VISIBLE
        ivSuccess?.visibility = View.GONE
        ivError?.visibility = View.GONE
        currentJob = CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            dismissDialog()
        }
    }

    suspend fun setSuccessMessage(text: String) {
        cancelCurrentJob()
        dismissDialog()
        startLoadingDialog()
        textView?.text = text
        progressBar?.visibility = View.GONE
        ivSuccess?.visibility = View.VISIBLE
        ivError?.visibility = View.GONE
        currentJob = CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            dismissDialog()
        }
    }

    suspend fun setErrorMessage(text: String) {
        cancelCurrentJob()
        dismissDialog()
        startLoadingDialog()
        textView?.text = text
        progressBar?.visibility = View.GONE
        ivSuccess?.visibility = View.GONE
        ivError?.visibility = View.VISIBLE
        currentJob = CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            dismissDialog()
        }
    }

    suspend fun setExceptionMessage(text: String?) {
        cancelCurrentJob()
        dismissDialog()
        startLoadingDialog()
        textView?.text = "If you see this message then contact the developer. \nError --> $text"
        progressBar?.visibility = View.GONE
        ivSuccess?.visibility = View.GONE
        ivError?.visibility = View.VISIBLE
        currentJob = CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            dismissDialog()
        }
    }

    fun dismissDialog() {
        cancelCurrentJob()
        progressBar?.visibility = View.VISIBLE
        ivSuccess?.visibility = View.GONE
        ivError?.visibility = View.GONE
        dialog?.dismiss()
        dialog = null
    }

    private fun cancelCurrentJob() {
        currentJob?.cancel()
        currentJob = null
    }
}

