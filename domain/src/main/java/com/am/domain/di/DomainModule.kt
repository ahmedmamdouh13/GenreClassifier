package com.am.domain.di

import com.am.domain.usecase.ClassifyTrackUseCase
import com.am.domain.usecase.ClassifyTrackUseCaseImpl
import org.koin.dsl.module


val DomainModule = module {
    single<ClassifyTrackUseCase> {
        ClassifyTrackUseCaseImpl(get(),get())
    }
}