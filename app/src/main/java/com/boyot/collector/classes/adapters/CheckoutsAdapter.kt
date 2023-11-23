package com.boyot.collector.classes.adapters

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boyot.collector.R
import com.boyot.collector.databinding.ItemCheckoutBinding
import com.boyot.collector.domain.entity.beans.InvoiceBean
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CheckoutsAdapter @Inject constructor(@ActivityContext val context: Context) :
    RecyclerView.Adapter<CheckoutsAdapter.ViewHolder>() {
    private val data: MutableList<InvoiceBean> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewCheckout: Int): ViewHolder = ViewHolder(
        ItemCheckoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = data[position].collection_date
        holder.binding.tvInvoiceId.text = Html.fromHtml(
            context.getString(R.string.operation_number_, data[position].invoice_number)
        )
        holder.binding.tvAddress.text = context.getString(R.string.address_, data[position].getFullAddress())
        holder.binding.tvCost.text =
        context.getString(R.string._egp, data[position].total_amount)
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

    fun calculateTotalCost():  Double {
        var totalCost: Double = 0.0
        for (invoice in data) {
            invoice.total_amount?.let {
                totalCost += it.toFloat()
            }
        }
        return totalCost

    }


    inner class ViewHolder(val binding: ItemCheckoutBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}