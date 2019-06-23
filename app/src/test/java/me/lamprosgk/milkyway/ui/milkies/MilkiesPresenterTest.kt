package me.lamprosgk.milkyway.ui.milkies

import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import me.lamprosgk.milkyway.TestDataProvider.milkies
import me.lamprosgk.milkyway.data.repository.MilkiesRepositoryImpl
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever


class MilkiesPresenterTest {

    @Mock
    lateinit var view: MilkiesContract.View
    @Mock
    lateinit var repository: MilkiesRepositoryImpl

    private lateinit var presenter: MilkiesContract.Presenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        // replace Schedulers for test
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        presenter = MilkiesPresenter(repository)
        presenter.setView(view)
    }

    @Test
    fun getImagesSuccess_InvokesCorrectMethods() {
        whenever(repository.getImages(anyString(), anyString(), anyString(), anyString())).thenReturn(
            Single.just(milkies))

        presenter.getImages()

        // check that the right methods called in the view and in right order
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showImages(milkies)
        verify(view, never()).showError(anyInt())
    }

    @Test
    fun getImagesFailure_RetriesThreeTimes() {
        val throwable = Throwable()

        whenever(repository.getImages(anyString(), anyString(), anyString(), anyString())).thenReturn(Single.error(throwable))
        presenter.getImages()

        // verify that method is called 4 times (initial call + 3 retries)
        verify(view, times(4)).showLoading()
    }

    @Test
    fun getImagesFailure_ErrorIsShown() {
        val throwable = Throwable()

        whenever(repository.getImages(anyString(), anyString(), anyString(), anyString())).thenReturn(Single.error(throwable))
        presenter.getImages()

        verify(view, atLeastOnce()).showLoading()
        verify(view, atLeastOnce()).hideLoading()
        verify(view).showError(anyInt())
        verify(view, never()).showImages(me.lamprosgk.milkyway.any())
    }
}