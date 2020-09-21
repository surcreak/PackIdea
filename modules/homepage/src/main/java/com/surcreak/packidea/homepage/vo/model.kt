package com.surcreak.packidea.homepage.vo

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.surcreak.packidea.base.vo.BaseVO

class TestModel(override val itemType: Int, val string: String): BaseVO(), MultiItemEntity {
    override fun isEmpty(): Boolean {
        return false
    }
}