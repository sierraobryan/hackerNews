package com.example.myapplication.ui.main

import com.atomicrobot.marsrover.ui.main.ShowStoryFragmentBinding
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.data.model.Item
import kotlinx.android.synthetic.main.fragment_story_layout.*

class ShowStoryFragment : DialogFragment() {

    companion object {
        fun newInstance() = ShowStoryFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var item: Item
    private lateinit var binding: ShowStoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_story_layout, container, false)

        binding.vm = viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()
            dialog?.window?.attributes = dialog?.window?.attributes?.apply {
                width = resources.getDimension(R.dimen.dialog_width).toInt()
                height = resources.getDimension(R.dimen.dialog_height).toInt()
            }
    }

}
