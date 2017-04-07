package com.wayloren.keddit

import android.app.Application
import com.wayloren.keddit.di.AppModule
import com.wayloren.keddit.di.DaggerNewsComponent
import com.wayloren.keddit.di.NewsComponent


class KedditApp : Application() {

    companion object {
        lateinit var newsComponent: NewsComponent
    }

    override fun onCreate() {
        super.onCreate()
        newsComponent = DaggerNewsComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}