package com.surcreak.packidea.base.application

import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context


class ApplicationDelegate : IAppLife {
    private lateinit var list: MutableList<IModuleConfig>
    private var appLifes: MutableList<IAppLife> = ArrayList()
    private var lifecycleCallbacks: MutableList<ActivityLifecycleCallbacks> = ArrayList()

    override fun attachBaseContext(base: Context) {
        val manifestParser = ManifestParser(base)
        list = manifestParser.parse()
        if (list.isNotEmpty()) {
            for (configModule in list) {
                configModule.injectAppLifecycle(base, appLifes)
                configModule.injectActivityLifecycle(base, lifecycleCallbacks)
            }
        }
        if (appLifes.isNotEmpty()) {
            for (life in appLifes) {
                life.attachBaseContext(base)
            }
        }
    }

    override fun onCreate(application: Application) {
        if (appLifes.isNotEmpty()) {
            for (life in appLifes) {
                life.onCreate(application)
            }
        }
        if (lifecycleCallbacks.isNotEmpty()) {
            for (life in lifecycleCallbacks) {
                application.registerActivityLifecycleCallbacks(life)
            }
        }
    }

    override fun onTerminate(application: Application) {
        if (appLifes.isNotEmpty()) {
            for (life in appLifes) {
                life.onTerminate(application)
            }
        }
        if (lifecycleCallbacks.isNotEmpty()) {
            for (life in lifecycleCallbacks) {
                application.unregisterActivityLifecycleCallbacks(life)
            }
        }
    }
}