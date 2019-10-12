package com.surcreak.packidea.homepage.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProviders
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.surcreak.packidea.base.ui.fragment.BaseFragment
import com.surcreak.packidea.homepage.R
import com.surcreak.packidea.homepage.vm.HomeViewModel
import com.surcreak.packidea.homepage.vo.TestModel
import kotlinx.android.synthetic.main.homepage_fragment_home.*

class HomeFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.homepage_fragment_home

    private val homeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    private val adapter by lazy {
        HomeAdapter1(mutableListOf())
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        refreshLayout.observeStatus(this, homeViewModel.test)
        recycleView.adapter = adapter

        homeViewModel.test()
    }
}

class HomeAdapter(layoutResId: Int, var datas: MutableList<TestModel>)
    : BaseQuickAdapter<TestModel, BaseViewHolder>(layoutResId, datas) {
    override fun convert(helper: BaseViewHolder?, item: TestModel?) {

    }
}

class HomeAdapter1(var datas: MutableList<TestModel>)
    : BaseMultiItemQuickAdapter<TestModel, BaseViewHolder>(datas) {

    init {
        addItemType(1, R.layout.homepage_home_item1)
        addItemType(2, R.layout.homepage_home_item2)
    }

    override fun convert(helper: BaseViewHolder?, item: TestModel?) {

    }
}