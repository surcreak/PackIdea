package com.surcreak.base

import android.app.Application
import kotlin.properties.Delegates

open class BaseApplication : Application() {

    companion object {
        var instance: BaseApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}