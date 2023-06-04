package io.github.ilyadreamix.httptools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.github.ilyadreamix.httptools.navigation.HTNavigationHost
import io.github.ilyadreamix.httptools.theme.HTTheme

/** Main HTTPTools activity. */
class HTActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HTTheme {
                HTNavigationHost()
            }
        }
    }
}
