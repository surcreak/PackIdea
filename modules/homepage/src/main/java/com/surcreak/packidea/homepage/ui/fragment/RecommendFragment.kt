package com.surcreak.packidea.homepage.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.surcreak.packidea.homepage.R
import com.surcreak.packidea.homepage.ui.adapter.RecommendAdapter
import com.surcreak.packidea.homepage.vm.RecommendViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.homepage_fragment_recommend.*

//@AndroidEntryPoint
class RecommendFragment : Fragment() {

    //override fun getLayoutId(): Int = R.layout.homepage_fragment_recommend

    //private val recommendViewModel : RecommendViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.homepage_fragment_recommend, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycleView.adapter = recommendAdapter
        recommendAdapter.setEmptyView(R.layout.default_list_empty)
        //recommendViewModel.test()
    }

    private val recommendAdapter by lazy {
        RecommendAdapter(mutableListOf())
    }

//    override fun onViewCreated(savedInstanceState: Bundle?) {
//
//        recycleView.adapter = recommendAdapter
//        recommendAdapter.setEmptyView(R.layout.default_list_empty)
//        //recommendViewModel.test()
//    }
}