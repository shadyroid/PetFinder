package com.shady.boyot.classes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.shady.domain.entity.beans.InvoiceBean
import com.shady.boyot.R
import com.shady.boyot.databinding.ItemInvoiceBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class InvoicesAdapter @Inject constructor(@ActivityContext val context: Context) :
    RecyclerView.Adapter<InvoicesAdapter.ViewHolder>() {
    lateinit var listener: Listener
    private val data: MutableList<InvoiceBean> = ArrayList()
    private var isFinishedLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewInvoice: Int): ViewHolder = ViewHolder(
        ItemInvoiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = data[position].name
        holder.binding.tvInvoiceId.text = data[position].id
        holder.binding.tvAddress.text = data[position].address
        holder.binding.tvCost.text = data[position].cost
        holder.binding.shimmer.root.visibility =
            if (!isFinishedLoading && position == data.size - 1) View.VISIBLE else View.GONE
        holder.binding.checkBox.isChecked = data[position].isSelected
    }


    override fun getItemCount(): Int {
        return data.size
    }

    fun addData(data: List<InvoiceBean>?) {
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

    fun setFinishedLoading(finishedLoading: Boolean) {
        isFinishedLoading = finishedLoading
        if (data.isNotEmpty()) notifyItemChanged(data.size - 1)
    }

    interface Listener {
        fun onInvoiceClick(invoice: InvoiceBean)
    }

    inner class ViewHolder(val binding: ItemInvoiceBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        init {
            itemView.setOnClickListener { onItemClick() }
            binding.checkBox.setOnClickListener { onItemClick() }
        }

        private fun onItemClick() {
            listener.onInvoiceClick(data[bindingAdapterPosition])
            data[bindingAdapterPosition].isSelected = !data[bindingAdapterPosition].isSelected
            notifyDataSetChanged()
        }
    }
}