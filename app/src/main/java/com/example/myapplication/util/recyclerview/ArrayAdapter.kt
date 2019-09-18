package com.example.myapplication.util.recyclerview

import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class ArrayAdapter<Item, ItemViewHolder : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<ItemViewHolder>() {

    private var items: List<Item> = ArrayList()

    fun setItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    protected fun getItemAtPosition(position: Int) = items[position]
}