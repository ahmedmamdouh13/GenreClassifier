package com.am.genreclassifier.di

import com.am.genreclassifier.GenreClassifier
import com.am.genreclassifier.MainViewModel
import com.am.librosa.Librosa
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val AppModule = module {
    viewModel {
        MainViewModel(get(), get(), get())
    }
    single {
        GenreClassifier(get())
    }
    single {
        Librosa()
    }
}