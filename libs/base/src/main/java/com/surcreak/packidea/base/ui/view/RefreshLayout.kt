package com.surcreak.packidea.base.ui.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
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

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val thisView = this
        val paddingLeft = thisView.paddingLeft
        val paddingTop = thisView.paddingTop
        val paddingBottom = thisView.paddingBottom

        var i = 0
        val len = super.getChildCount()
        while (i < len) {
            val child = super.getChildAt(i)

            if (child.visibility == View.GONE || "GONE" == child.getTag(com.scwang.smart.refresh.layout.kernel.R.id.srl_tag)) {
                i++
                continue
            }

            if (mRefreshContent != null && mRefreshContent.view === child) {
                val isPreviewMode =
                    thisView.isInEditMode && mEnablePreviewInEditMode && isEnableRefreshOrLoadMore(
                        mEnableRefresh
                    ) && mRefreshHeader != null
                val contentView = mRefreshContent.view
                val lp = contentView.layoutParams
                val mlp = if (lp is ViewGroup.MarginLayoutParams) lp else sDefaultMarginLP
                val left = paddingLeft + mlp.leftMargin
                var top = paddingTop + mlp.topMargin
                val right = left + contentView.measuredWidth
                var bottom = top + contentView.measuredHeight
                if (isPreviewMode && isEnableTranslationContent(
                        mEnableHeaderTranslationContent,
                        mRefreshHeader
                    )
                ) {
                    top += mHeaderHeight
                    bottom += mHeaderHeight
                }

                contentView.layout(left, top, right, bottom)
            }
            if (mRefreshHeader != null && mRefreshHeader.view === child) {
                val isPreviewMode =
                    thisView.isInEditMode && mEnablePreviewInEditMode && isEnableRefreshOrLoadMore(
                        mEnableRefresh
                    )
                val headerView = mRefreshHeader.view
                val lp = headerView.layoutParams
                val mlp = if (lp is ViewGroup.MarginLayoutParams) lp else sDefaultMarginLP
                val left = mlp.leftMargin
                var top = mlp.topMargin + mHeaderInsetStart
                val right = left + headerView.measuredWidth
                var bottom = top + headerView.measuredHeight
                if (!isPreviewMode) {
                    if (mRefreshHeader.spinnerStyle === SpinnerStyle.Translate) {
                        top = top - mHeaderHeight
                        bottom = bottom - mHeaderHeight
                        /*
                         * SpinnerStyle.Scale  headerView.getMeasuredHeight() 已经重复处理
                         **/
                        //                    } else if (mRefreshHeader.getSpinnerStyle().scale && mSpinner > 0) {
                        //                        bottom = top + Math.max(Math.max(0, isEnableRefreshOrLoadMore(mEnableRefresh) ? mSpinner : 0) - lp.bottomMargin - lp.topMargin, 0);
                    }
                }
                headerView.layout(left, top, right, bottom)
            }
            if (mRefreshFooter != null && mRefreshFooter.view === child) {
                val isPreviewMode =
                    thisView.isInEditMode && mEnablePreviewInEditMode && isEnableRefreshOrLoadMore(
                        mEnableLoadMore
                    )
                val footerView = mRefreshFooter.view
                val lp = footerView.layoutParams
                val mlp = if (lp is ViewGroup.MarginLayoutParams) lp else sDefaultMarginLP
                val style = mRefreshFooter.spinnerStyle
                val left = mlp.leftMargin
                var top = mlp.topMargin + thisView.measuredHeight - mFooterInsetStart
                if (mFooterNoMoreData && mFooterNoMoreDataEffective && mEnableFooterFollowWhenNoMoreData && mRefreshContent != null
                    && mRefreshFooter.spinnerStyle === SpinnerStyle.Translate
                    && isEnableRefreshOrLoadMore(mEnableLoadMore)
                ) {
                    val contentView = mRefreshContent.view
                    val clp = contentView.layoutParams
                    val topMargin = if (clp is ViewGroup.MarginLayoutParams) clp.topMargin else 0
                    top = paddingTop + paddingTop + topMargin + contentView.measuredHeight
                }

                if (style === SpinnerStyle.MatchLayout) {
                    top = mlp.topMargin - mFooterInsetStart
                } else if (isPreviewMode
                    || style === SpinnerStyle.FixedFront
                    || style === SpinnerStyle.FixedBehind
                ) {
                    top -= mFooterHeight
                } else if (style.scale && mSpinner < 0) {
                    top -= Math.max(
                        if (isEnableRefreshOrLoadMore(mEnableLoadMore)) -mSpinner else 0,
                        0
                    )
                }

                val right = left + footerView.measuredWidth
                val bottom = top + footerView.measuredHeight
                footerView.layout(left, top, right, bottom)
            }

            if (child == statusView) {
                child.layout(l, t, r, b)
            }
            i++
        }
    }

    fun <T : BaseVO> observeStatus(owner: LifecycleOwner, liveData: StateLiveData<T>) {
        liveData.observe(owner, Observer {
            if (oldDataStatus == it.status) {
                return@Observer
            }
            statusView.removeAllViews()
            when (it.status) {
                DataStatus.LOADING -> {
                    statusView.addView(loadingView, RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT))
                }
                DataStatus.ERROR -> {
                    statusView.addView(errorView, RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT))
                    finishRefresh()
                }
                DataStatus.SUCCESS -> {
                    if (it.data?.isEmpty() != false) {
                        statusView.addView(emptyView, RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.MATCH_PARENT))
                    }
                    finishRefresh()
                }
                else -> { }
            }
            oldDataStatus = it.status
        })
    }
}