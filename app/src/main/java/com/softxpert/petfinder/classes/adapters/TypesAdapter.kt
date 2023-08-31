package com.softxpert.petfinder.classes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softxpert.petfinder.R
import com.softxpert.domain.entity.beans.TypeBean
import com.softxpert.petfinder.databinding.ItemTypeBinding
import javax.inject.Inject

class TypesAdapter @Inject constructor() :
    RecyclerView.Adapter<TypesAdapter.ViewHolder>() {
     lateinit var listener: Listener
    private val data: MutableList<TypeBean> = ArrayList()
    var selectedIndex: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemTypeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvType.text = data[position].name
        holder.binding.cvType.setCardBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                if (selectedIndex == position) R.color.purple_200 else R.color.white
            )
        )

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<TypeBean>?) {
        val lastIndex = if (this.data.isEmpty()) 0 else this.data.size - 1
        this.data.addAll(data!!)
        val newLastIndex = this.data.size
        notifyItemRangeChanged(lastIndex, newLastIndex)
    }


    interface Listener {
        fun onTypeClick(type: String)
    }

    inner class ViewHolder(val binding: ItemTypeBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        init {
            itemView.setOnClickListener { onItemClick() }
        }

        private fun onItemClick() {
            listener.onTypeClick(data[bindingAdapterPosition].name)
            selectedIndex = bindingAdapterPosition;
            notifyDataSetChanged()
        }
    }
}