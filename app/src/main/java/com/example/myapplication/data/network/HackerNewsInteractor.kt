package com.example.myapplication.data.network

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.data.model.Item
import io.reactivex.Observable
import retrofit2.Response
import timber.log.Timber
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import io.reactivex.functions.Function


class HackerNewsInteractor (
    private val context: Context,
    private val service: HackerNewsApiService
) {

    class LoadStoriesRequest()
    class LoadStoriesResponse(val request: LoadStoriesRequest, val items: List<Item>)

    fun loadStories(request: LoadStoriesRequest): Observable<LoadStoriesResponse> {
        return service.getTopStories()
            .toObservable()
            .map { response -> checkResponse(response, context.getString(R.string.error)) }
            .map { response -> response.body() ?: emptyList() }
            .flatMap { ids -> loadStoriesFromIds(ids) }
            .map { stories -> LoadStoriesResponse(request, stories) }
            .doOnError { error -> Timber.e(error) }
    }

    private fun loadStoriesFromIds(ids: List<Int>): Observable<List<Item>> {
        val allObservables: MutableList<Observable<Item>> = mutableListOf()
        for (i in 1..10) {
            allObservables.add(i - 1, loadStory(ids[i]))
        }
        return Observable.zip(allObservables)
             { t ->  convertToListOfItems(t) }
    }

    private fun convertToListOfItems(array: Array<Any>) : List<Item> {
        return array.toList() as List<Item>
    }

    private fun loadStory(id : Int) : Observable<Item> {
        return service.getItem(id).toObservable()
            .map { response -> checkResponse(response, context.getString(R.string.error)) }
            .map { response -> response.body() }
    }


    private fun <T> checkResponse(response: Response<T>, message: String): Response<T> {
        return when {
            response.isSuccessful -> response
            else -> throw IllegalStateException(message)
        }
    }
}