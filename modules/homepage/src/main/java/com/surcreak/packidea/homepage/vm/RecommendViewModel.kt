package com.surcreak.packidea.homepage.vm

import androidx.hilt.lifecycle.ViewModelInject
import com.surcreak.packidea.base.vm.BaseViewModel
import com.surcreak.packidea.base.vm.StateLiveData
import com.surcreak.packidea.common.data.UnsplashRepository
import com.surcreak.packidea.homepage.vo.TestModel

class RecommendViewModel @ViewModelInject constructor(
    private val repository: UnsplashRepository
) : BaseViewModel() {

    private val _test = StateLiveData<TestModel>()
    val test: StateLiveData<TestModel> = _test

    fun test() {
        //_test.postLoading()
        _test.postSuccess(TestModel(2, "home"))
//        _test.postError(Exception())
    }
}