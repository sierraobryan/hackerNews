package com.example.myapplication.ui.main

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.BR
import com.example.myapplication.data.model.Item
import com.example.myapplication.data.network.HackerNewsInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val app: Application,
                                       private val hackerNewsInteractor: HackerNewsInteractor)
    : BaseViewModel(app) {

    sealed class Stories {
        class Loading : Stories()
        class Result(val stories: List<Item>) : Stories()
        class Error(val message: String) : Stories()
    }

    @Bindable
    fun isLoading(): Boolean = state is Stories.Loading

    @Bindable
    fun getStories() = state.let {
        when (it) {
            is Stories.Result -> it.stories
            else -> emptyList()
        }
    }

    var state : Stories = Stories.Loading()
        set(value) {
            field = value

            notifyPropertyChanged(BR.loading)
            notifyPropertyChanged(BR.stories)

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

}
