package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atomicrobot.marsrover.ui.main.MainFragmentBinding
import com.example.myapplication.R
import com.example.myapplication.StoryItemBinding
import com.example.myapplication.data.model.Item
import com.example.myapplication.util.recyclerview.ArrayAdapter

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        binding.vm = viewModel


        binding.commits.layoutManager = LinearLayoutManager(activity)
        binding.commits.adapter = StoryAdapter(viewModel)

        viewModel.fetchTopStories()

        return binding.root
    }

    private class StoryAdapter(val viewModel: MainViewModel) : ArrayAdapter<Item, ItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: StoryItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_row_story_summary, parent, false)
            return ItemViewHolder(binding, viewModel)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val story = getItemAtPosition(position)
            holder.bind(story, position)
        }

    }

    private class ItemViewHolder(private val binding: StoryItemBinding,
                                 private val viewModel: MainViewModel): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, position: Int) {
            binding.item = item
            binding.position = position
            binding.vm = viewModel
            binding.executePendingBindings()
        }
    }

}
