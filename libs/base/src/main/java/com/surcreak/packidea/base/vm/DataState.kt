package com.surcreak.packidea.base.vm

import androidx.annotation.NonNull
import androidx.annotation.Nullable

class DataState<T> {

    @NonNull
    @get:NonNull
    var status: DataStatus = DataStatus.LOADING
        private set

    @Nullable
    @get:Nullable
    var data: T? = null
        private set

    @Nullable
    @get:Nullable
    var error: Throwable? = null
        private set

    var progress = 0

    init {
        this.data = null
        this.error = null
    }

    fun loading(progress: Int): DataState<T> {
        this.status = DataStatus.LOADING
        this.data = null
        this.error = null
        this.progress = progress
        return this
    }

    fun success(@NonNull data: T): DataState<T> {
        this.status = DataStatus.SUCCESS
        this.data = data
        this.error = null
        this.progress = 100
        return this
    }

    fun error(@NonNull error: Throwable): DataState<T> {
        this.status = DataStatus.ERROR
        this.data = null
        this.error = error
        this.progress = 100
        return this
    }

}