package com.wayloren.keddit.di

import com.wayloren.keddit.KedditApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: KedditApp) {

    @Provides
    @Singleton
    fun provideContext() = app

    @Provides
    @Singleton
    fun provideApplication() = app
}