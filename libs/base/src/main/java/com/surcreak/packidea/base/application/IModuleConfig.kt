package com.surcreak.packidea.base.application

import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context


interface IModuleConfig {
    fun injectAppLifecycle(context: Context, iAppLifes: MutableList<IAppLife>)

    fun injectActivityLifecycle(context: Context, lifecycleCallbackses: MutableList<ActivityLifecycleCallbacks>)
}