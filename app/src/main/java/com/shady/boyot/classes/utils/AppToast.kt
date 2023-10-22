package com.shady.boyot.classes.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.shady.boyot.databinding.ToastLayoutBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class AppToast @Inject constructor(@ActivityContext val context: Context) {
    private val binding: ToastLayoutBinding =
        ToastLayoutBinding.inflate(LayoutInflater.from(context))
    private val toast: Toast = Toast(context)

    init {
        toast.setGravity(Gravity.TOP, 0, 16)
        toast.duration = Toast.LENGTH_LONG
        toast.view = binding.root
    }

    private fun setText(text: String?) {
        binding.tvErrorMessage.text = text
    }

    private fun show() {
        if (view.isShown) {
            toast.cancel()
            Handler(Looper.getMainLooper()).postDelayed({ toast.show() }, 100)
        } else {
            toast.show()
        }
    }

    fun showMessage(body: String) {
        if (body.isEmpty()) return
        setText(body)
        show()
    }

    fun cancel() {
        if (view.isShown) {
            toast.cancel()
        }
    }


    val view: View
        get() = toast.view!!
}