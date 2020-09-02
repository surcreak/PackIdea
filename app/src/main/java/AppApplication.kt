package com.surcreak.packidea

import android.content.Context
import androidx.multidex.MultiDex
import com.surcreak.packidea.base.application.BaseApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}