package com.surcreak.packidea.base.extensions

import android.widget.RelativeLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.surcreak.packidea.base.vm.DataStatus
import com.surcreak.packidea.base.vm.StateLiveData
import com.surcreak.packidea.base.vo.BaseVO

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
