package com.surcreak.packidea.mainpage

import android.app.Application
import android.content.Context
import com.surcreak.packidea.base.application.IAppLife
import com.surcreak.packidea.base.application.IModuleConfig

class MainPageApplication : IModuleConfig, IAppLife{

    override fun attachBaseContext(base: Context) {
    }

    override fun onCreate(application: Application) {
    }

    override fun onTerminate(application: Application) {
    }


    override fun injectAppLifecycle(context: Context, iAppLifes: MutableList<IAppLife>) {
        iAppLifes += this
    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycleCallbackses: MutableList<Application.ActivityLifecycleCallbacks>) {
    }

}