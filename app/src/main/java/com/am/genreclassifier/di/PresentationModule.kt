package com.am.genreclassifier.di

import com.am.genreclassifier.mapper.GenreUiStateMapper
import com.am.genreclassifier.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val PresentationModule = module {
    viewModel {
        MainViewModel(get(), get(), get())
    }
    single{
       GenreUiStateMapper()
    }
}