package com.parthdesai1208.compose

import android.app.Application
import com.parthdesai1208.compose.model.accessibility.AppContainer
import com.parthdesai1208.compose.model.accessibility.AppContainerImpl

class ComposeApp : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}