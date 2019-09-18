package com.example.myapplication.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.atomicrobot.marsrover.ui.main.MainActivityBinding
import com.example.myapplication.ui.NavigationEvent
import android.content.Intent
import android.net.Uri
import com.example.myapplication.R


class MainActivity : BaseActivity() {

    private final var SHOW_STORY_TAG : String = "showStoryFragment"
    private final var SHOW_COMMENTS_TAG : String = "showCommentFragment"

    private lateinit var viewModel : MainViewModel
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = getViewModel(MainViewModel::class)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.vm = viewModel
        binding.executePendingBindings()

        viewModel.navigationEvent.observe(this, object : NavigationEvent.NavigationObserver<MainViewModel.StoryNavigation> {
            override fun onNavigationEvent(event: MainViewModel.StoryNavigation) {
                when (event) {
                    is MainViewModel.StoryNavigation.OpenWebPage -> openWebPage(event.url)
                    MainViewModel.StoryNavigation.OpenStory -> openStory()
                    MainViewModel.StoryNavigation.OpenComments -> openComments()
                }
            }
        })

    }

    private fun openWebPage(url : String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun openStory() {
        ShowStoryFragment.newInstance().show(supportFragmentManager, SHOW_STORY_TAG)
    }

    private fun openComments() {
        ShowCommentsFragment.newInstance().show(supportFragmentManager, SHOW_COMMENTS_TAG)
    }

}
