package com.luckytrip.luckytrip.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luckytrip.luckytrip.databinding.RecyclerDestinationItemBinding
import com.luckytrip.luckytrip.models.Destination

// Recycler view item click listener
typealias ItemClickListener = ((view: View, position: Int) -> Unit)?

class DestinationsAdapter() :
    RecyclerView.Adapter<DestinationsAdapter.DestinationViewHolder>() {

    var itemClickListener: ItemClickListener = null

    private val destinations: MutableList<Destination> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerDestinationItemBinding.inflate(inflater, parent, false)
        return DestinationViewHolder(binding)
    }

    override fun getItemCount(): Int = destinations.size

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) =
        holder.bind(destinations[position])

    inner class DestinationViewHolder(private val binding: RecyclerDestinationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                invokeClickEvent(it)
            }
        }
        fun bind(item: Destination) {
            binding.destination = item
        }

        private fun invokeClickEvent(view: View) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                itemClickListener?.invoke(view, adapterPosition)
            }
        }
    }


    fun submitList(newDestinations: List<Destination>) {
        destinations.clear()
        destinations.addAll(newDestinations)
        notifyDataSetChanged()
    }

    fun setSelection(position: Int) {
        destinations[position].selected = !destinations[position].selected
        notifyItemChanged(position)
    }
}