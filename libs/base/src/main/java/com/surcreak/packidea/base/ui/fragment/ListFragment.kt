package com.surcreak.packidea.base.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

abstract class ListFragment : BaseFragment() {

    abstract fun getRecycleView(): RecyclerView


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}