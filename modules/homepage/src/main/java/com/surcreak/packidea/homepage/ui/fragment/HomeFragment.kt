package com.surcreak.packidea.homepage.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.surcreak.packidea.base.ui.fragment.BaseFragment
import com.surcreak.packidea.base.vm.DataStatus
import com.surcreak.packidea.homepage.R
import com.surcreak.packidea.homepage.vm.HomeViewModel
import com.surcreak.packidea.homepage.vo.TestModel
import kotlinx.android.synthetic.main.homepage_fragment_home.*

class HomeFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.homepage_fragment_home

    private val homeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private val adapter by lazy {
        HomeAdapter1(mutableListOf())
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        refreshLayout.observeStatus(this, homeViewModel.test)
        recycleView.adapter = adapter
        adapter.setEmptyView(R.layout.default_list_empty)

        refreshLayout.setOnRefreshListener { homeViewModel.test() }
        observer()
        homeViewModel.test()
    }

    private fun observer() {
        homeViewModel.test.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                DataStatus.SUCCESS -> {
                    adapter.addData(it.data!!)
                }
                DataStatus.UNKONW -> TODO()
                DataStatus.ERROR -> {

                }
                DataStatus.LOADING -> TODO()
            }
        })
    }
}

class HomeAdapter(layoutResId: Int, var datas: MutableList<TestModel>)
    : BaseQuickAdapter<TestModel, BaseViewHolder>(layoutResId, datas) {

    override fun convert(holder: BaseViewHolder, item: TestModel) {
        TODO("Not yet implemented")
    }
}

class HomeAdapter1(var datas: MutableList<TestModel>)
    : BaseMultiItemQuickAdapter<TestModel, BaseViewHolder>(datas) {

    init {
        addItemType(1, R.layout.homepage_home_item1)
        addItemType(2, R.layout.homepage_home_item2)
    }

    override fun convert(holder: BaseViewHolder, item: TestModel) {

    }
}