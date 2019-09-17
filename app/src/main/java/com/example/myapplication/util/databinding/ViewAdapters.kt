package com.example.myapplication.util.databinding

import android.view.View
import androidx.databinding.BindingAdapter

object ViewAdapters {
    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(view: View, string: String) {
        view.visibility = if (string.isNotBlank()) View.VISIBLE else View.GONE
    }
}