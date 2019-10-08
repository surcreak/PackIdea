package com.surcreak.packidea.mainpage.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.surcreak.packidea.base.vm.BaseViewModel

class SlideshowViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text
}