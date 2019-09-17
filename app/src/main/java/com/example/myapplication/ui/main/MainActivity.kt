package com.example.myapplication.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.atomicrobot.marsrover.ui.main.MainActivityBinding
import com.example.myapplication.R


class MainActivity : BaseActivity() {

    private lateinit var viewModel : MainViewModel
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = getViewModel(MainViewModel::class)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.vm = viewModel
        binding.executePendingBindings()

    }

}
