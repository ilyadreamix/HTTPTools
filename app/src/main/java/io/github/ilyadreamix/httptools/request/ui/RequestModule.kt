package io.github.ilyadreamix.httptools.request.ui

import org.koin.dsl.module

val requestModule = module {
    single { RequestViewModel() }
}
