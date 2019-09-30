package com.surcreak.base.extensions

import android.view.View

fun Boolean.visibleOrGone(): Int = if (this) View.VISIBLE else View.GONE

fun Boolean?.visibleOrGone(): Int = if (this == true) View.VISIBLE else View.GONE

fun Boolean.visibleOrGone(action: () -> Unit): Int =
    if (this) {
        action()
        View.VISIBLE
    } else View.GONE
