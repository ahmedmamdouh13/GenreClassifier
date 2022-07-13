package com.am.genreclassifier.di

import com.am.genreclassifier.GenreClassifier
import com.am.genreclassifier.MainViewModel
import com.am.genreclassifier.domain.ClassifyTrackUseCase
import com.am.genreclassifier.domain.ClassifyTrackUseCaseImpl
import com.am.librosa.data.util.LibrosaHelper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val AppModule = module {
    single<ClassifyTrackUseCase> {
        ClassifyTrackUseCaseImpl(get(),get())
    }
    viewModel {
        MainViewModel(get())
    }
}