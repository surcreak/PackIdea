package com.surcreak.packidea.homepage.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surcreak.packidea.base.vm.StateLiveData
import com.surcreak.packidea.homepage.vo.TestModel
import java.lang.Exception

class HomeViewModel : ViewModel() {

    private val _test = StateLiveData<TestModel>()
    val test: StateLiveData<TestModel> = _test

    fun test() {
        //_test.postLoading()
        _test.postSuccess(TestModel(2, "home"))
//        _test.postError(Exception())
    }
}