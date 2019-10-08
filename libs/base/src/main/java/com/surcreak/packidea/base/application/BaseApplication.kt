package com.surcreak.packidea.base.application

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

open class BaseApplication : Application() {

    private lateinit var applicationDelegate: ApplicationDelegate

    companion object {
        var instance: BaseApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        applicationDelegate = ApplicationDelegate()
        applicationDelegate.attachBaseContext(base!!)
    }
}