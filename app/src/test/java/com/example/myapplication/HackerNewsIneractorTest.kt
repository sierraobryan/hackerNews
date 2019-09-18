package com.example.myapplication

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.data.room.ItemStore
import com.example.myapplication.data.network.HackerNewsApiService
import com.example.myapplication.data.network.HackerNewsInteractor
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class HackerNewsIneractorTest {

    @Mock
    lateinit var api: HackerNewsApiService

    @Mock
    lateinit var store : ItemStore

    private lateinit var interactor: HackerNewsInteractor

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val context = ApplicationProvider.getApplicationContext<Application>()
        interactor = HackerNewsInteractor(context, api, store)
    }

    @Test
    @Throws(Exception::class)
    fun testLoadStories() {
//        val mockResponse = Single.just(Response.success(
////            listOf(
////            Item(1, false, "story", "name", 1, false, listOf(), 2, "", "", "", listOf(), 1, 1))
////        ))
        val mockResponse = Single.just(Response.success(listOf(1)))
        whenever(api.getTopStories()).thenReturn(mockResponse)

        val subscriber = TestObserver<HackerNewsInteractor.LoadStoriesResponse>()
        interactor.loadStories(HackerNewsInteractor.LoadStoriesRequest()).subscribeWith(subscriber)
        subscriber.await(1, TimeUnit.SECONDS)

        subscriber.assertValueCount(1)
        subscriber.assertNoErrors()
        subscriber.assertComplete()

        val response = subscriber.values()[0]
        assertEquals(1, response)
    }
}