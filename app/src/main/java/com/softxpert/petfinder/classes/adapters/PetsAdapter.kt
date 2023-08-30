package com.softxpert.petfinder.classes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softxpert.petfinder.R
import com.softxpert.petfinder.classes.rest.models.beans.PetBean
import com.softxpert.petfinder.databinding.ItemPetBinding

class PetsAdapter(private val listener: Listener) :
    RecyclerView.Adapter<PetsAdapter.ViewHolder>() {
    private val data: MutableList<PetBean> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewPet: Int): ViewHolder = ViewHolder(
        ItemPetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(data[position].displaySmallImage)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(holder.binding.ivImage)
        holder.binding.tvName.text = "${holder.itemView.context.getString(R.string.name)}: ${data[position].displayName}"
        holder.binding.tvGender.text = "${holder.itemView.context.getString(R.string.gender)}: ${data[position].displayGender}"
        holder.binding.tvType.text = "${holder.itemView.context.getString(R.string.type)}${data[position].displayType}"

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addData(data: List<PetBean>?) {
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
        fun onPetClick(pet: PetBean)
    }

    inner class ViewHolder(val binding: ItemPetBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        init {
            itemView.setOnClickListener { onItemClick() }
        }

        private fun onItemClick() {
            listener.onPetClick(data[bindingAdapterPosition])
        }
    }
}