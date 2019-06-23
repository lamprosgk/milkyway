package me.lamprosgk.milkyway.ui.milkies

import me.lamprosgk.milkyway.*
import me.lamprosgk.milkyway.domain.model.Milky
import me.lamprosgk.milkyway.ui.BasePresenter
import me.lamprosgk.milkyway.ui.BaseView

/**
 * MVP contract between presenter and view for Images list screen
 */
interface MilkiesContract {

    interface View : BaseView<Presenter> {
        fun showImages(milkies: List<Milky>)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * Default arguments provided, but allows for any search term
         */
        fun getImages(searchTerm: String = SEARCH_TERM_DEFAULT,
                      mediaType: String = MEDIA_TYPE_IMAGE,
                      startYear: String = YEAR_FROM_DEFAULT,
                      endYear: String = YEAR_TO_DEFAULT
        )
    }
}