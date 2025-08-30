package com.amritthakur.newsapp.di

import android.app.Application
import com.amritthakur.newsapp.NewsApplication
import com.amritthakur.newsapp.presentation.navigation.NavigationChannel
import com.amritthakur.newsapp.presentation.viewmodel.HomeViewModel
import com.amritthakur.newsapp.presentation.viewmodel.NewsViewModel
import com.amritthakur.newsapp.presentation.viewmodel.SourcesViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        DispatcherModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    // Expose functions
    fun navigationChannel(): NavigationChannel
    fun homeViewModel(): HomeViewModel
    fun newsViewModel(): NewsViewModel
    fun sourcesViewModel(): SourcesViewModel

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}