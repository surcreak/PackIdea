package com.surcreak.packidea.homepage.ui.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.surcreak.packidea.base.ui.fragment.BaseFragment
import com.surcreak.packidea.homepage.R
import com.surcreak.packidea.homepage.ui.adapter.HomeViewPagerAdapter
import com.surcreak.packidea.homepage.vm.HomeViewModel
import com.surcreak.packidea.homepage.vo.HomePager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.homepage_fragment_home.*

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.homepage_fragment_home

    private val homeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private val viewPagerAdapter by lazy {
        HomeViewPagerAdapter(this,
            listOf(
                HomePager.RECOMMEND,
                HomePager.HOT,
                HomePager.VIDEO,
                HomePager.MUSIC
            ))
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        pager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = viewPagerAdapter.pagers[position].text
        }.attach()
    }
}