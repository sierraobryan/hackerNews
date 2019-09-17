package com.example.myapplication.ui.main

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.data.model.Item
import com.example.myapplication.data.network.HackerNewsInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val app: Application,
                    private val hackerNewsInteractor: HackerNewsInteractor,
                    protected val disposables: CompositeDisposable = CompositeDisposable()
)
    : AndroidViewModel(app), Observable {

    @VisibleForTesting
    internal var stories: Stories = Stories.Result(emptyList())
        set(value) {
            field = value

            when (value) {
                is Stories.Error -> value.message
            }
        }

    sealed class Stories {
        class Loading : Stories()
        class Result(val stories: List<Item>) : Stories()
        class Error(val message: String) : Stories()
    }

    fun fetchTopStories() {
        stories = Stories.Loading()
        disposables.add(hackerNewsInteractor.loadStories(HackerNewsInteractor.LoadStoriesRequest())
            .map { it.items }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { stories = Stories.Result(it) },
                { stories = Stories.Error(it.message!!) }
            ))
    }



    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
