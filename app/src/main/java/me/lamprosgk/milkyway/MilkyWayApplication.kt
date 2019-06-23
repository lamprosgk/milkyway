package me.lamprosgk.milkyway

import android.app.Application
import me.lamprosgk.milkyway.di.*

class MilkyWayApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = createComponent()
    }

    private fun createComponent(): AppComponent =
        DaggerAppComponent.builder()
            .mapperModule(MapperModule())
            .repositoryModule(RepositoryModule())
            .presenterModule(PresenterModule())
            .build()
}