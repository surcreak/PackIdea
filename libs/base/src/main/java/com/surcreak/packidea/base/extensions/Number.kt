package com.surcreak.packidea.base.extensions

import android.content.res.Resources
import kotlin.math.ceil

//**************************  Int  ********************************
fun Int.sp2px(): Int = (this * Resources.getSystem().displayMetrics.scaledDensity + 0.5F).toInt()

val Int.px2dp: Int
    get() = toDouble().toDp()

val Int.dp2px: Int
    get() = toDouble().dp2px

val Int.sp2px: Int
    get() = sp2px()

//**************************  Double  ********************************
fun Double.toDp(): Int = ceil((this / Resources.getSystem().displayMetrics.density)).toInt()
fun Double.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

val Double.px2dp: Int
    get() = toDp()

val Double.dp2px: Int
    get() = toPx()
