package com.surcreak.base.vm

import androidx.lifecycle.LiveData

open class StateLiveData<T> : LiveData<DataState<T>>() {

    /**
     * Use this to put the Data on a LOADING Status
     */
    fun postLoading(progress: Int = 0) {
        postValue(DataState<T>().loading(progress))
    }

    /**
     * Use this to put the Data on a ERROR DataStatus
     * @param throwable the error to be handled
     */
    fun postError(throwable: Throwable) {
        postValue(DataState<T>().error(throwable))
    }

    /**
     * Use this to put the Data on a SUCCESS DataStatus
     * @param data
     */
    fun postSuccess(data: T) {
        postValue(DataState<T>().success(data))
    }
}