package com.example.myapplication.util.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.util.recyclerview.ArrayAdapter

object RecyclerViewAdapters {

    @JvmStatic
    @BindingAdapter("items")
    fun setItems(view: RecyclerView, items: List<Any>) {
        @Suppress("UNCHECKED_CAST")
        val arrayAdapter = view.adapter as ArrayAdapter<Any, RecyclerView.ViewHolder>
        arrayAdapter.setItems(items)
    }
}