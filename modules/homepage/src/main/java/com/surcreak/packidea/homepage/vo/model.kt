package com.surcreak.packidea.homepage.vo

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.surcreak.packidea.base.vo.BaseVO

class TestModel(val name: String) : BaseVO(), MultiItemEntity {

    override fun getItemType(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEmpty(): Boolean {
        return true
    }

}