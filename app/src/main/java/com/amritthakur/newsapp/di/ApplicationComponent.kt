package com.amritthakur.newsapp.di

import android.app.Application
import com.amritthakur.newsapp.NewsApplication
import com.amritthakur.newsapp.presentation.navigation.NavigationChannel
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

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}