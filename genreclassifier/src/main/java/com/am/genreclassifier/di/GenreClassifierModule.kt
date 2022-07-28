package com.am.genreclassifier.di

import com.am.genreclassifier.GenreClassifier
import com.am.genreclassifier.data.GenreClassifierRepositoryImpl
import com.am.genreclassifier.data.GenreClassifierRepository
import com.am.genreclassifier.data.cache.DatabaseHelper
import org.koin.dsl.module


val GenreClassifierModule = module {
    single<GenreClassifierRepository> {
        GenreClassifierRepositoryImpl(get(), get())
    }
    single {
        GenreClassifier(get())
    }

    single {
        DatabaseHelper(get())
    }

}