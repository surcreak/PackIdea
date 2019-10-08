package com.surcreak.packidea.homepage.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.surcreak.packidea.base.ui.fragment.BaseFragment
import com.surcreak.packidea.homepage.R
import com.surcreak.packidea.homepage.vm.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_home

    private lateinit var homeViewModel: HomeViewModel

    override fun onViewCreated(savedInstanceState: Bundle?) {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.text.observe(this, Observer {
            text_home.text = it
        })
    }
}