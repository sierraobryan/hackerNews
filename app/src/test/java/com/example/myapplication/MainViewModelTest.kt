package com.example.myapplication

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.data.model.Item
import com.example.myapplication.data.network.HackerNewsInteractor
import com.example.myapplication.ui.main.MainViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @JvmField @Rule val trampolineSchedulerRule = TrampolineSchedulerRule()

    @Mock private lateinit var interactor: HackerNewsInteractor

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val app = ApplicationProvider.getApplicationContext<Application>()
        viewModel = MainViewModel(
            app,
            interactor)
    }


    @Test
    fun testFetchItems() {
        val mockResult = mock(HackerNewsInteractor.LoadStoriesResponse::class.java)
        val mockItem = mock(Item::class.java)
        whenever(mockResult.items).thenReturn(listOf(mockItem))
        whenever(interactor.loadStories(any())).thenReturn(Observable.just(mockResult))

        assertTrue((viewModel.state as? MainViewModel.Stories.Result) == null)
        viewModel.fetchTopStories()
        assertTrue((viewModel.state as? MainViewModel.Stories.Result)?.stories?.size == 1)
    }
}