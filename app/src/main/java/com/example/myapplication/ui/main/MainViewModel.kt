package com.example.myapplication.ui.main

import android.app.Application
import android.text.Html
import androidx.annotation.VisibleForTesting
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.BR
import com.example.myapplication.data.model.Item
import com.example.myapplication.data.network.HackerNewsInteractor
import com.example.myapplication.ui.NavigationEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(app: Application,
                                       private val hackerNewsInteractor: HackerNewsInteractor)
    : BaseViewModel(app) {

    sealed class Stories {
        class Loading : Stories()
        class Result(val stories: List<Item>) : Stories()
        class Error(val message: String) : Stories()
    }

    sealed class Comments {
        class Loading : Comments()
        class Result(val comments: List<Item>) : Comments()
        class Error(val message: String) : Comments()
    }


    lateinit var item : Item

    @Bindable
    fun isLoading(): Boolean = state is Stories.Loading

    @Bindable
    fun getStories() = state.let {
        when (it) {
            is Stories.Result -> it.stories
            else -> emptyList()
        }
    }

    @Bindable
    fun getComments() = commentState.let {
        when (it) {
            is Comments.Result -> it.comments
            else -> emptyList()
        }
    }

    @VisibleForTesting
    internal var state : Stories = Stories.Loading()
        set(value) {
            field = value

            notifyPropertyChanged(BR.loading)
            notifyPropertyChanged(BR.stories)

        }

    var commentState : Comments = Comments.Loading()
        set(value) {
            field = value

            notifyPropertyChanged(BR.loading)
            notifyPropertyChanged(BR.comments)

        }

    fun fetchTopStories() {
        state = Stories.Loading()
        disposables.add(hackerNewsInteractor.loadStories(HackerNewsInteractor.LoadStoriesRequest())
            .map { it.items }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { state = Stories.Result(it) },
                { state = Stories.Error(it.message!!) }
            ))
    }

    fun fetchComments() {
        commentState = Comments.Loading()
        item.kids?.let {
            disposables.add(hackerNewsInteractor.loadComments(
                HackerNewsInteractor.LoadCommentsRequest(
                    item.kids!!
                )
            )
                .map { it.items }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { commentState = Comments.Result(it) },
                    { commentState = Comments.Error(it.message!!) }
                ))
        }
    }

    sealed class StoryNavigation {
        data class OpenWebPage(val url : String) : StoryNavigation()
        object OpenStory : StoryNavigation()
        object OpenComments : StoryNavigation()
    }

    val navigationEvent = NavigationEvent<StoryNavigation>()

    fun promptMoreInformation(item: Item) {
        this.item = item
        navigationEvent.value = if (!item.url.isNullOrEmpty()) StoryNavigation.OpenWebPage(item.url) else StoryNavigation.OpenStory
    }

    fun startShowComments(item: Item) {
        this.item = item
        navigationEvent.value = StoryNavigation.OpenComments
    }

}
