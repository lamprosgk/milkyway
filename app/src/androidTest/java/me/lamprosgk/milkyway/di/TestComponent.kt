package me.lamprosgk.milkyway.di

import dagger.Component
import me.lamprosgk.milkyway.ui.milkies.MilkiesActivityTest
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, MapperModule::class, TestRepositoryModule::class, PresenterModule::class])
interface TestComponent : AppComponent {
    fun inject(target: MilkiesActivityTest)
}