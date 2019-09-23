package com.example.myapplication.ui.main

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R

open class BaseDialog :DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        this.setStyle(STYLE_NORMAL, R.style.Panel)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val size = Point()
        val display = dialog?.window?.windowManager?.defaultDisplay
        display?.getSize(size)
        val heightTotal = size.y
        dialog?.window?.attributes = dialog?.window?.attributes?.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = (heightTotal * 0.92).toInt()
            gravity = Gravity.BOTTOM
            y = 0
        }
    }

}