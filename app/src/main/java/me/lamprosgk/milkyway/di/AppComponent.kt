package me.lamprosgk.milkyway.di

import dagger.Component
import me.lamprosgk.milkyway.ui.milkies.MilkiesActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, MapperModule::class, RepositoryModule::class, PresenterModule::class])
interface AppComponent {
    fun inject(target: MilkiesActivity)
}