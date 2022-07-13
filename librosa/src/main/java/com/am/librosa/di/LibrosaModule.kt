package com.am.librosa.di

import com.am.librosa.data.util.LibrosaHelper
import com.am.librosa.data.util.JLibrosa
import com.am.librosa.data.LibrosaRepositoryImpl
import com.am.librosa.domain.LibrosaRepository
import org.koin.dsl.module


val LibrosaModule = module {
    single<LibrosaRepository> {
        LibrosaRepositoryImpl(get(), get(), get())
    }
    single {
        LibrosaHelper()
    }
    single {
        JLibrosa()
    }
}