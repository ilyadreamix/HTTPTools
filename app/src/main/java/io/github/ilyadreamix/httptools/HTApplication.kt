package io.github.ilyadreamix.httptools

import android.app.Application
import io.github.ilyadreamix.httptools.home.homeModule
import io.github.ilyadreamix.httptools.request.requestModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/** HTTPTools [Application] class. */
class HTApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@HTApplication)
            modules(homeModule, requestModule)
        }
    }
}
