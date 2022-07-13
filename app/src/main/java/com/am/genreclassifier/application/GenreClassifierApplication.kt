package com.am.genreclassifier.application

import android.app.Application
import com.am.genreclassifier.di.AppModule
import com.am.genreclassifier.di.GenreClassifierModule
import com.am.librosa.di.LibrosaModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GenreClassifierApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GenreClassifierApplication)
            modules(AppModule,
                GenreClassifierModule,
                LibrosaModule
                )
        }
    }
}