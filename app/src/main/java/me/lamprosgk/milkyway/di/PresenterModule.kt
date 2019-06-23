package me.lamprosgk.milkyway.di

import dagger.Module
import dagger.Provides
import me.lamprosgk.milkyway.domain.repository.MilkiesRepository
import me.lamprosgk.milkyway.ui.milkies.MilkiesPresenter
import me.lamprosgk.milkyway.ui.milkies.MilkiesContract
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideImagesPresenter(imagesRepository: MilkiesRepository): MilkiesContract.Presenter =
        MilkiesPresenter(imagesRepository)
}