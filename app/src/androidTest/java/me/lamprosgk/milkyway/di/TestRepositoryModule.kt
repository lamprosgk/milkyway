package me.lamprosgk.milkyway.di

import dagger.Module
import dagger.Provides
import me.lamprosgk.milkyway.domain.repository.MilkiesRepository
import org.mockito.Mockito
import javax.inject.Singleton

@Module
class TestRepositoryModule {

    @Provides
    @Singleton
    fun provideImagesRepository(): MilkiesRepository = Mockito.mock(MilkiesRepository::class.java)
}