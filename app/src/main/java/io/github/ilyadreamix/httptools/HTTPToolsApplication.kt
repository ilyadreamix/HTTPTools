package io.github.ilyadreamix.httptools

import android.app.Application
import io.github.ilyadreamix.httptools.home.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/** HTTPTools [Application] class. */
class HTTPToolsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@HTTPToolsApplication)
            modules(homeModule)
        }
    }
}
