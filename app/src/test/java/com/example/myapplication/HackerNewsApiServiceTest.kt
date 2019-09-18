package com.example.myapplication

import com.example.myapplication.data.model.Item
import com.example.myapplication.data.DataModule
import com.example.myapplication.data.network.HackerNewsApiService
import io.reactivex.observers.TestObserver
import junit.framework.Assert.*
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class HackerNewsApiServiceTest {

    private lateinit var server: MockWebServer

    private var listStoriesAsString = "[\n" +
            "  1\n" +
            "]"

    private var singleItemAsString = "{\n" +
            "  \"by\" : \"dhouston\",\n" +
            "  \"descendants\" : 71,\n" +
            "  \"id\" : 8863,\n" +
            "  \"kids\" : [ 9224, 8917, 8952, 8884, 8887, 8869, 8958, 8940, 8908, 9005, 8873, 9671, 9067, 9055, 8865, 8881, 8872, 8955, 10403, 8903, 8928, 9125, 8998, 8901, 8902, 8907, 8894, 8870, 8878, 8980, 8934, 8943, 8876 ],\n" +
            "  \"score\" : 104,\n" +
            "  \"time\" : 1175714200,\n" +
            "  \"title\" : \"My YC app: Dropbox - Throw away your USB drive\",\n" +
            "  \"type\" : \"story\",\n" +
            "  \"url\" : \"http://www.getdropbox.com/u/2/screencast.html\"\n" +
            "}"

    @Before
    fun setup() {
        server = MockWebServer()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        server.shutdown()
    }

    @Test
    @Throws(Exception::class)
    fun testListStoriesSuccessful() {
        server.enqueue(MockResponse().setBody(listStoriesAsString))
        server.start()

        val api = buildApi(server)
        val subscriber = TestObserver<Response<List<Int>>>()
        api.getTopStories().subscribe(subscriber)
        subscriber.await(1, TimeUnit.SECONDS)

        val serverRequest = server.takeRequest()
        assertEquals("GET", serverRequest.method)
        assertEquals("/topstories.json", serverRequest.path)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValueCount(1)
        val response = subscriber.values()[0]
        assertTrue(response.isSuccessful)
        val stories = response.body()
        assertEquals(1, stories!!.size.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testStoryByIdSuccessful() {
        server.enqueue(MockResponse().setBody(singleItemAsString))
        server.start()

        val api = buildApi(server)
        val subscriber = TestObserver<Response<Item>>()
        api.getItem(8863).subscribe(subscriber)
        subscriber.await(1, TimeUnit.SECONDS)

        val serverRequest = server.takeRequest()
        assertEquals("GET", serverRequest.method)
        assertEquals("/item/8863.json", serverRequest.path)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValueCount(1)
        val response = subscriber.values()[0]
        assertTrue(response.isSuccessful)
        val story = response.body()
        assertEquals(8863, story!!.id)
    }

    @Test
    @Throws(Exception::class)
    fun testListStoriesUnsuccessful() {
        server.enqueue(MockResponse().setResponseCode(404).setBody("{\"message\": \"Not Found\"}"))
        server.start()

        val api = buildApi(server)
        val subscriber = TestObserver<Response<List<Int>>>()
        api.getTopStories().subscribe(subscriber)
        subscriber.await(1, TimeUnit.SECONDS)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValueCount(1)
        val response = subscriber.values()[0]
        assertFalse(response.isSuccessful)
        assertEquals(404, response.code().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testListStoriesNetworkError() {
        val api = buildApi("http://bad_url/")
        val subscriber = TestObserver<Response<List<Int>>>()
        api.getTopStories().subscribe(subscriber)
        subscriber.await(1, TimeUnit.SECONDS)

        subscriber.assertNoValues()
        assertEquals(1, subscriber.errors().size.toLong())
        val error = subscriber.errors()[0]
        assertTrue(error is UnknownHostException)
        // Note: You can't compare message text because that will be provided by the underlying runtime
    }

    @Throws(Exception::class)
    private fun buildApi(server: MockWebServer): HackerNewsApiService {
        val baseUrl = server.url("")
        return buildApi(baseUrl.toString())
    }

    @Throws(Exception::class)
    private fun buildApi(baseUrl: String): HackerNewsApiService {
        val module = DataModule()
        val client = OkHttpClient.Builder().build()
        val converterFactory = module.provideConverter()
        val retrofit = module.provideRetrofit(client, baseUrl, converterFactory)
        return module.provideHackerNewsApiService(retrofit)
    }
}