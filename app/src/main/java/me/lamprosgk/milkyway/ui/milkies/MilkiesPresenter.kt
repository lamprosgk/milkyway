package me.lamprosgk.milkyway.ui.milkies

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.lamprosgk.milkyway.domain.repository.MilkiesRepository
import me.lamprosgk.milkyway.ui.ErrorMapper

class MilkiesPresenter constructor(private val imagesRepository: MilkiesRepository) :
    MilkiesContract.Presenter {

    private var subscription: Disposable? = null
    private var view: MilkiesContract.View? = null

    override fun setView(view: MilkiesContract.View?) {
        this.view = view
    }

    override fun getImages(searchTerm: String, mediaType: String, startYear: String, endYear: String) {
        checkNotNull(view)

        subscription = imagesRepository.getImages(searchTerm, mediaType, startYear, endYear)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.showLoading() }
            .doOnSuccess { view?.hideLoading() }
            .doOnError { view?.hideLoading() }
            .retry(3)   // retry 3 times
            .subscribe(
                // onNext
                { view?.showImages(it) },
                // onError
                {
                    view?.showError(ErrorMapper.getMessage(it))
                })
    }

    override fun onDestroy() {
        view = null
        subscription?.dispose()
    }
}