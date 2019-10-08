package com.surcreak.packidea.base.ui.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.surcreak.packidea.base.R
import com.surcreak.packidea.base.vm.DataStatus
import com.surcreak.packidea.base.vm.StateLiveData
import com.surcreak.packidea.base.vo.BaseVO

class RefreshLayout @JvmOverloads constructor(context: Context,
                                              attrs: AttributeSet? = null)
    :SmartRefreshLayout(context, attrs) {

    val statusView by lazy {
        LinearLayout(context).apply {
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            isClickable = false
            gravity = Gravity.CENTER
        }
    }

    val emptyView by lazy {
        LayoutInflater.from(context).inflate(R.layout.default_empty, null)
    }

    init {
        addView(statusView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        statusView.layout(l, t, r, b)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            val emptyView = LayoutInflater.from(context).inflate(R.layout.default_empty, null)
            emptyView.draw(canvas)
        }
    }

    override fun getChildCount(): Int {
        return super.getChildCount() - 1
    }

    fun observeStatu(owner: LifecycleOwner, liveData: StateLiveData<BaseVO>){
        liveData.observe(owner, Observer {
            statusView.removeAllViews()
            when (it.status) {
                DataStatus.SUCCESS -> {
                    if (it.data?.isEmpty() != false) {
                        statusView.addView(emptyView)
                    }
                }
                DataStatus.ERROR -> {
                    statusView.addView(emptyView)
                }
                DataStatus.LOADING -> {
                    statusView.addView(emptyView)
                }
                null -> {}
            }
        })
    }
}