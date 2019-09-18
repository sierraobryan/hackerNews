package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atomicrobot.marsrover.ui.main.ShowCommentsFragmentBinding
import com.atomicrobot.marsrover.ui.main.ShowStoryFragmentBinding
import com.example.myapplication.R
import com.example.myapplication.StoryItemBinding
import com.example.myapplication.data.model.Item
import com.example.myapplication.util.recyclerview.ArrayAdapter

class ShowCommentsFragment : DialogFragment() {

    companion object {
        fun newInstance() = ShowCommentsFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ShowCommentsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_comments, container, false)

        binding.vm = viewModel


        binding.comments.layoutManager = LinearLayoutManager(activity)
        binding.comments.adapter = CommentAdapter(viewModel)

        viewModel.fetchComments()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.attributes = dialog?.window?.attributes?.apply {
            width = resources.getDimension(R.dimen.dialog_width).toInt()
            height = resources.getDimension(R.dimen.dialog_height).toInt()
        }
    }

    private class CommentAdapter (val viewModel: MainViewModel) : ArrayAdapter<Item, ItemViewHolder>() {

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
            binding.executePendingBindings()
        }
    }


}