package com.boyot.collector.classes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boyot.collector.databinding.ItemBuildingBinding
import com.boyot.collector.domain.entity.beans.IdNameBean
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class OptionsAdapter@Inject constructor(@ActivityContext context: Context) :
    RecyclerView.Adapter<OptionsAdapter.ViewHolder>() {
    private var buildings: List<IdNameBean> = ArrayList()
    lateinit var listener: Listener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemBuildingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.btnBuilding.text = buildings[position].name
    }

    override fun getItemCount(): Int {
        return buildings.size
    }

    fun setData(buildings: List<IdNameBean>) {
        this.buildings = buildings
        notifyDataSetChanged()
    }

    interface Listener {
        fun onBuildingClick(building: IdNameBean?)
    }

    inner class ViewHolder(var binding: ItemBuildingBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        init {
            itemView.setOnClickListener { onItemClick() }
        }

        private fun onItemClick() {
            listener.onBuildingClick(buildings[bindingAdapterPosition])
        }
    }
}