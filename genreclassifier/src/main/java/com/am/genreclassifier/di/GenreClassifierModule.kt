package com.am.genreclassifier.di

import com.am.genreclassifier.GenreClassifier
import com.am.genreclassifier.data.GenreClassifierRepositoryImpl
import com.am.domain.repo.GenreClassifierRepository
import org.koin.dsl.module


val GenreClassifierModule = module {
    single<GenreClassifierRepository> {
        GenreClassifierRepositoryImpl(get())
    }
    single {
        GenreClassifier(get())
    }
}