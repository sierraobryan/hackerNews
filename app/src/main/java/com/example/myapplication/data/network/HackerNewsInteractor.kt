package com.example.myapplication.data.network

import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.data.model.Item
import io.reactivex.Observable
import retrofit2.Response
import timber.log.Timber
import kotlin.math.min


class HackerNewsInteractor (
    private val context: Context,
    private val service: HackerNewsApiService
) {

    class LoadStoriesRequest()
    class LoadStoriesResponse(val request: LoadStoriesRequest, val items: List<Item>)

    class LoadCommentsRequest(val ids: List<Int>)
    class LoadCommentsResponse(val request: LoadCommentsRequest, val items: List<Item>)

    fun loadStories(request: LoadStoriesRequest): Observable<LoadStoriesResponse> {
        return service.getTopStories()
            .toObservable()
            .map { response -> checkResponse(response, context.getString(R.string.error)) }
            .map { response -> response.body() ?: emptyList() }
            .flatMap { ids -> loadItemsFromIds(ids) }
            .map { stories -> LoadStoriesResponse(request, stories) }
            .doOnError { error -> Timber.e(error) }
    }

    private fun loadItemsFromIds(ids: List<Int>): Observable<List<Item>> {
        val allObservables: MutableList<Observable<Item>> = mutableListOf()
        ids.forEach { allObservables.add(loadItem(it)) }
        return Observable.zip(allObservables.take(min(ids.size, 30)))
             { t ->  convertToListOfItems(t) }
    }

    private fun convertToListOfItems(array: Array<Any>) : List<Item> {
        return array.toList() as List<Item>
    }

    private fun loadItem(id : Int) : Observable<Item> {
        return service.getItem(id).toObservable()
            .map { response -> checkResponse(response, context.getString(R.string.error)) }
            .map { response -> response.body() }
    }

    fun loadComments(request: LoadCommentsRequest): Observable<LoadCommentsResponse> {
        return loadItemsFromIds(request.ids)
            .map { comments -> LoadCommentsResponse(request, comments) }
            .doOnError { error -> Timber.e(error) }
    }


    private fun <T> checkResponse(response: Response<T>, message: String): Response<T> {
        return when {
            response.isSuccessful -> response
            else -> throw IllegalStateException(message)
        }
    }
}