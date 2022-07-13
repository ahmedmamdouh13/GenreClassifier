package com.am.genreclassifier.di

import com.am.genreclassifier.MainViewModel
import com.am.domain.usecase.ClassifyTrackUseCase
import com.am.domain.usecase.ClassifyTrackUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val AppModule = module {
    viewModel {
        MainViewModel(get())
    }
}