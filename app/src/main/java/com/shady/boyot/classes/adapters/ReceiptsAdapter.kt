package com.shady.boyot.classes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shady.boyot.R
import com.shady.domain.entity.beans.ReceiptBean
import com.shady.boyot.databinding.ItemReceiptBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ReceiptsAdapter @Inject constructor(@ActivityContext val context: Context) :
    RecyclerView.Adapter<ReceiptsAdapter.ViewHolder>() {
    lateinit var listener: Listener
    private val data: MutableList<ReceiptBean> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewReceipt: Int): ViewHolder = ViewHolder(
        ItemReceiptBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = data[position].client_name
        holder.binding.tvReceiptId.text = data[position].receipt
        holder.binding.tvAddress.text = data[position].address
        holder.binding.tvCost.text = context.getString(R.string._egp, data[position].total_amount)   }


    override fun getItemCount(): Int {
        return data.size
    }

    fun addData(data: List<ReceiptBean>?) {
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


    interface Listener {
        fun onReceiptClick(receipt: ReceiptBean)
    }

    inner class ViewHolder(val binding: ItemReceiptBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        init {
            itemView.setOnClickListener { onItemClick() }
        }

        private fun onItemClick() {
            listener.onReceiptClick(data[bindingAdapterPosition])
        }
    }
}