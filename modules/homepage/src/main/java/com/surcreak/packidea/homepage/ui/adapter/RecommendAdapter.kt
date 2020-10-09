package com.surcreak.packidea.homepage.ui.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.surcreak.packidea.homepage.R
import com.surcreak.packidea.homepage.vo.TestModel

class RecommendAdapter(var datas: MutableList<TestModel>)
    : BaseMultiItemQuickAdapter<TestModel, BaseViewHolder>(datas) {

    init {
        addItemType(1, R.layout.homepage_recommend_item1)
        addItemType(2, R.layout.homepage_recommend_item2)
    }

    override fun convert(holder: BaseViewHolder, item: TestModel) {

    }

}