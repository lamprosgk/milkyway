package me.lamprosgk.milkyway.di

import dagger.Module
import dagger.Provides
import me.lamprosgk.milkyway.data.mapper.Mapper
import me.lamprosgk.milkyway.data.remote.model.Item
import me.lamprosgk.milkyway.data.remote.api.NasaService
import me.lamprosgk.milkyway.data.repository.MilkiesRepositoryImpl
import me.lamprosgk.milkyway.domain.model.Milky
import me.lamprosgk.milkyway.domain.repository.MilkiesRepository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideImagesRepository(service: NasaService, mapper: Mapper<Item, Milky>): MilkiesRepository =
        MilkiesRepositoryImpl(service, mapper)
}