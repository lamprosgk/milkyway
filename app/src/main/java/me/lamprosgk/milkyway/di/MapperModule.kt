package me.lamprosgk.milkyway.di

import dagger.Module
import dagger.Provides
import me.lamprosgk.milkyway.data.mapper.Mapper
import me.lamprosgk.milkyway.data.mapper.SearchResultMapper
import me.lamprosgk.milkyway.data.remote.model.Item
import me.lamprosgk.milkyway.domain.model.Milky
import javax.inject.Singleton

@Module
class MapperModule {

    @Provides
    @Singleton
    fun provideSearchResultMapper(): Mapper<Item, Milky> = SearchResultMapper()
}