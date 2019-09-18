package com.example.myapplication.util.databinding

import android.view.View
import android.webkit.WebView
import androidx.databinding.BindingAdapter

object ViewAdapters {
    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("showString")
    fun setVisible(view: View, string: String?) {
        view.visibility = if (!string.isNullOrEmpty()) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("webViewData")
    fun setWebView(view: WebView, string: String?) {
        view.loadDataWithBaseURL(null, string, "text/html", "utf-8", null);
    }
}