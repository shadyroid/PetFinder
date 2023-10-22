package com.shady.boyot.classes.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import com.shady.boyot.databinding.DialogLoadingBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class LoadingDialog @Inject constructor(@ActivityContext context: Context) {
    private val binding: DialogLoadingBinding
    private val dialog: Dialog

    init {
        dialog = Dialog(context)
        dialog.setCanceledOnTouchOutside(true)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogLoadingBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    fun show() {
        dialog.setCancelable(true)
        if (!dialog.isShowing) dialog.show()
    }

    fun dismiss() {
        if (dialog.isShowing) dialog.dismiss()
    }
}