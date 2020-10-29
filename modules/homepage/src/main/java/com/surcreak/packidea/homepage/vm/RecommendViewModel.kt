package com.surcreak.packidea.homepage.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.surcreak.packidea.base.vm.BaseViewModel
import com.surcreak.packidea.base.vm.StateLiveData
import com.surcreak.packidea.common.data.UnsplashPhoto
import com.surcreak.packidea.common.data.UnsplashRepository
import com.surcreak.packidea.homepage.vo.TestModel
import kotlinx.coroutines.flow.Flow

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

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<UnsplashPhoto>>? = null

    fun searchPictures(queryString: String): Flow<PagingData<UnsplashPhoto>> {
        currentQueryValue = queryString
        val newResult: Flow<PagingData<UnsplashPhoto>> =
            repository.getSearchResultStream(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}