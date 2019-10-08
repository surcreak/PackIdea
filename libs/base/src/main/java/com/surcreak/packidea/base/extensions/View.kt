package com.surcreak.packidea.base.extensions

import android.view.View

fun View?.letGone() {
    this?.visibility = View.GONE
}

fun View?.letVisible(){
    this?.visibility = View.VISIBLE
}

fun View?.letInvisible(){
    this?.visibility = View.INVISIBLE
}