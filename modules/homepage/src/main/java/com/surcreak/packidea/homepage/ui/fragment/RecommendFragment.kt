package com.surcreak.packidea.homepage.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.surcreak.packidea.base.ui.fragment.BaseFragment
import com.surcreak.packidea.base.utils.Logger
import com.surcreak.packidea.homepage.R
import com.surcreak.packidea.homepage.ui.adapter.RecommendAdapter
import com.surcreak.packidea.homepage.vm.RecommendViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.homepage_fragment_recommend.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecommendFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.homepage_fragment_recommend

    private var searchJob: Job? = null
    private val recommendViewModel : RecommendViewModel by viewModels()
    private val recommendAdapter = RecommendAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.homepage_fragment_recommend, container, false)
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        recycleView.adapter = recommendAdapter
        //recommendAdapter.setEmptyView(R.layout.default_list_empty)
        recommendViewModel.test()
        search("Apple")
    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        Logger.d(TAG, "search")
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            recommendViewModel.searchPictures(query).collectLatest {
                recommendAdapter.submitData(it)
                Logger.d(TAG, "search it=$it")
            }
        }
    }
}