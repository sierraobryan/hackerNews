package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.atomicrobot.marsrover.ui.main.ShowStoryFragmentBinding
import com.example.myapplication.R

class ShowStoryFragment : BaseDialog() {

    companion object {
        fun newInstance() = ShowStoryFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ShowStoryFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        this.setStyle(STYLE_NORMAL, R.style.Panel)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_story_layout, container, false)

        binding.vm = viewModel

        return binding.root
    }

}
