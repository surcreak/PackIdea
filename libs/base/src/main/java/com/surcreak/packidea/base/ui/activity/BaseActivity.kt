package com.surcreak.packidea.base.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.HiltAndroidApp


abstract class BaseActivity: AppCompatActivity () {
    abstract fun getLayoutId(): Int
    abstract fun onViewCreated(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        onViewCreated(savedInstanceState)
    }


}