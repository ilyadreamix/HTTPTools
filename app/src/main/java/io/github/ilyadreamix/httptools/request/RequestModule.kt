package io.github.ilyadreamix.httptools.request

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val requestModule = module {
    viewModel { RequestViewModel() }
}
