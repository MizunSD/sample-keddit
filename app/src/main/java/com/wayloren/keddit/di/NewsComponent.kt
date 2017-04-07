package com.wayloren.keddit.di

import com.wayloren.keddit.NewsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NewsModule::class,
        NetworkModule::class
))
interface NewsComponent {
    fun inject(newsFragment: NewsFragment)
}