package com.surcreak.packidea.base.ui.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.util.SmartUtil
import com.scwang.smart.refresh.layout.wrapper.RefreshContentWrapper
import com.surcreak.packidea.base.R
import com.surcreak.packidea.base.vm.DataStatus
import com.surcreak.packidea.base.vm.StateLiveData
import com.surcreak.packidea.base.vo.BaseVO

class RefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SmartRefreshLayout(context, attrs) {

    var oldDataStatus = DataStatus.LOADING

    private val statusView by lazy {
        RelativeLayout(context).apply {
            isClickable = false
//            orientation = LinearLayout.VERTICAL
//            gravity = Gravity.CENTER
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    var emptyViewLayoutID = R.layout.default_list_empty
    var errorViewLayoutID = R.layout.default_list_error
    var loadingViewLayoutID = R.layout.default_list_loading

    private val emptyView: View by lazy {
        LayoutInflater.from(context).inflate(emptyViewLayoutID, null)
    }

    private val errorView: View by lazy {
        LayoutInflater.from(context).inflate(errorViewLayoutID, null)
    }

    private val loadingView: View by lazy {
        LayoutInflater.from(context).inflate(loadingViewLayoutID, null)
    }

    init {
        addView(statusView)
    }

    fun <T : BaseVO> observeStatus(owner: LifecycleOwner, liveData: StateLiveData<T>) {
        liveData.observe(owner, Observer {
            if (oldDataStatus == it.status) {
                return@Observer
            }

            if (mRefreshContent == null) {
                mRefreshContent = RefreshContentWrapper(errorView)
            }

            statusView.removeAllViews()
            when (it.status) {
                DataStatus.LOADING -> {
//                    statusView.addView(
//                        loadingView, RelativeLayout.LayoutParams(
//                            RelativeLayout.LayoutParams.MATCH_PARENT,
//                            RelativeLayout.LayoutParams.MATCH_PARENT
//                        )
//                    )
                }
                DataStatus.ERROR -> {
//                    statusView.addView(
//                        errorView, RelativeLayout.LayoutParams(
//                            RelativeLayout.LayoutParams.MATCH_PARENT,
//                            RelativeLayout.LayoutParams.MATCH_PARENT
//                        )
//                    )
                    finishRefresh()
                }
                DataStatus.SUCCESS -> {
                    if (it.data?.isEmpty() != false) {
//                        statusView.addView(
//                            emptyView, RelativeLayout.LayoutParams(
//                                RelativeLayout.LayoutParams.MATCH_PARENT,
//                                RelativeLayout.LayoutParams.MATCH_PARENT
//                            )
//                        )
                    }
                    finishRefresh()
                }
                else -> {
                }
            }
            oldDataStatus = it.status
        })
    }
}