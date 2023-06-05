package io.github.ilyadreamix.httptools.home

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val homeModule = module {
    single { HomeViewModel(androidContext().filesDir) }
}
