package com.boyot.collector.classes.adapters

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boyot.collector.R
import com.boyot.collector.databinding.ItemUnitBinding
import com.boyot.collector.domain.entity.beans.UnitBean
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class UnitsAdapter @Inject constructor(@ActivityContext val context: Context) :
    RecyclerView.Adapter<UnitsAdapter.ViewHolder>() {
    private val data: MutableList<UnitBean> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewUnit: Int): ViewHolder = ViewHolder(
        ItemUnitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvContractName.text = data[position].contract
        holder.binding.tvBuildingNumber.text = data[position].address
        holder.binding.tvUnitNumber.text = data[position].unit_number
        holder. binding.tvRegion.text = data[position].region
    }


    override fun getItemCount(): Int {
        return data.size
    }

    fun addData(data: List<UnitBean>?) {
        val lastIndex = if (this.data.isEmpty()) 0 else this.data.size - 1
        this.data.addAll(data!!)
        val newLastIndex = this.data.size
        notifyItemRangeChanged(lastIndex, newLastIndex)
    }

    fun clear() {
        val count = data.size
        data.clear()
        notifyItemRangeRemoved(0, count)
    }


    inner class ViewHolder(val binding: ItemUnitBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}