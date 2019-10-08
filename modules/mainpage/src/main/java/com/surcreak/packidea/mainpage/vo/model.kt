package com.surcreak.packidea.mainpage.vo

import com.surcreak.packidea.base.vo.BaseVO

class TestModel(val name: String) : BaseVO() {

    override fun isEmpty(): Boolean {
        return true
    }

}