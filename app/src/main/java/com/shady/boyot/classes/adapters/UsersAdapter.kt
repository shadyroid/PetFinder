package com.shady.boyot.classes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shady.boyot.databinding.ItemUserBinding
import com.shady.domain.entity.beans.UserBean
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class UsersAdapter @Inject constructor(@ActivityContext val context: Context) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    lateinit var listener: Listener
    private val data: MutableList<UserBean> = ArrayList()
    private var isFinishedLoading = false
    private var selectedIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewUser: Int): ViewHolder = ViewHolder(
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = data[position].actor_name
        holder.binding.tvId.text = data[position].actor_id
        holder.binding.tvContractNumber.text = data[position].contract_number
        holder.binding.tvPhone.text = data[position].phone
        holder.binding.tvAddress.text = data[position].address
        holder.binding.tvCost.text = data[position].cost
        holder.binding.shimmer.root.visibility =
            if (!isFinishedLoading && position == data.size - 1) View.VISIBLE else View.GONE

        holder.binding.radioButton.isChecked = position == selectedIndex
    }


    override fun getItemCount(): Int {
        return data.size
    }

    fun addData(data: List<UserBean>?) {
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
        fun onUserClick(user: UserBean)
    }

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        init {
            itemView.setOnClickListener { onItemClick() }
            binding.radioButton.setOnClickListener { onItemClick() }
        }

        private fun onItemClick() {
            listener.onUserClick(data[bindingAdapterPosition])
            selectedIndex = bindingAdapterPosition
            notifyDataSetChanged()
        }
    }
}