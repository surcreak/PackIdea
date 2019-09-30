package com.surcreak.base.extensions

import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.surcreak.base.vm.DataStatus
import com.surcreak.base.vm.StateLiveData
import com.surcreak.base.vo.BaseVO

fun RelativeLayout?.observeStatu(owner: LifecycleOwner, liveData: StateLiveData<BaseVO>){
    liveData.observe(owner, Observer {
        when (it.status) {
            DataStatus.SUCCESS -> {
                if (it.data?.isEmpty() != false) {
                    
                }
            }
            DataStatus.ERROR -> {

            }
            DataStatus.LOADING -> {

            }
            null -> {}
        }
    })
}
