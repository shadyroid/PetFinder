package com.shady.boyot.classes.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.widget.LinearLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import com.shady.boyot.classes.adapters.BuildingsAdapter
import com.shady.boyot.databinding.DialogBuildingsBinding
import com.shady.domain.entity.beans.BuildingBean
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class BuildingsDialog @Inject constructor(
    @ActivityContext context: Context,
    val buildingsAdapter: BuildingsAdapter
) {
    private val binding: DialogBuildingsBinding
    private val dialog: Dialog

    init {
        dialog = Dialog(context)
        dialog.setCanceledOnTouchOutside(true)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogBuildingsBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        binding.rvBuildings.itemAnimator = DefaultItemAnimator()
        binding.rvBuildings.isNestedScrollingEnabled = true
        binding.rvBuildings.adapter = buildingsAdapter
        binding.ivClose.setOnClickListener { dismiss() }
    }

    fun show() {
        dialog.show()
    }
    fun setListener(listener: BuildingsAdapter.Listener) {
        buildingsAdapter.listener = listener
    }

    fun dismiss() {
        dialog.dismiss()
    }

    fun setData(places: MutableList<BuildingBean>?) {
        places?.let {
            buildingsAdapter.setData(it)
        }
    }

}