package com.softxpert.petfinder.classes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.softxpert.domain.entity.beans.PetBean
import com.softxpert.petfinder.R
import com.softxpert.petfinder.databinding.ItemPetBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class PetsAdapter @Inject constructor(@ActivityContext val context: Context) :
    RecyclerView.Adapter<PetsAdapter.ViewHolder>() {
    lateinit var listener: Listener
    private val data: MutableList<PetBean> = ArrayList()
    private var isFinishedLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewPet: Int): ViewHolder = ViewHolder(
        ItemPetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        loadImage(data[position].displaySmallImage, holder.binding.ivImage)

        holder.binding.tvName.text =context.getString(R.string.name_is,data[position].displayName)

        holder.binding.tvGender.text =
            context.getString(R.string.gender_is,data[position].displayGender)
        holder.binding.tvType.text =
            context.getString(R.string.type_is,data[position].displayType)
        holder.binding.shimmer.root.visibility =
            if (!isFinishedLoading && position == data.size - 1) View.VISIBLE else View.GONE
    }

    private fun loadImage(imagePath: String?, imageView: ImageView) {
        val drawable = CircularProgressDrawable(
            context
        )
        drawable.centerRadius = 30f
        drawable.strokeWidth = 5f
        drawable.start()
        Glide.with(context)
            .load(imagePath)
            .placeholder(drawable)
            .error(R.drawable.app_logo)
            .into(imageView)
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

    fun setFinishedLoading(finishedLoading: Boolean) {
        isFinishedLoading = finishedLoading
        if (data.isNotEmpty()) notifyItemChanged(data.size - 1)
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