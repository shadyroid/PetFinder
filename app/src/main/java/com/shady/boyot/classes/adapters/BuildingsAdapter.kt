package com.shady.boyot.classes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shady.boyot.databinding.ItemBuildingBinding
import com.shady.domain.entity.beans.BuildingBean
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class BuildingsAdapter@Inject constructor(@ActivityContext context: Context) :
    RecyclerView.Adapter<BuildingsAdapter.ViewHolder>() {
    private var buildings: List<BuildingBean> = ArrayList()
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

    fun setData(buildings: List<BuildingBean>) {
        this.buildings = buildings
        notifyDataSetChanged()
    }

    interface Listener {
        fun onBuildingClick(building: BuildingBean?)
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