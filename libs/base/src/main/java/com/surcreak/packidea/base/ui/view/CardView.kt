package com.surcreak.packidea.base.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import android.view.View
import android.view.ViewOutlineProvider
import com.surcreak.packidea.base.extensions.dp2px

class CardView @JvmOverloads constructor(context: Context,
                                         attrs: AttributeSet? = null,
                                         defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private val paint by lazy {
        Paint().apply {
            color = Color.WHITE
            isAntiAlias = true
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        }
    }

    private val shadowPaint by lazy {
        Paint().apply {
            color = Color.LTGRAY
            isAntiAlias = true
            setShadowLayer(shadowRadius, dx, dy, shadowColor)
        }
    }

    private val RoundPaint by lazy {
        Paint().apply {
            color = Color.LTGRAY
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    private var radius = 14.dp2px.toFloat()
    private var shadowWidth = 5.dp2px
    private var shadowRadius = 5.dp2px.toFloat()
    private var shadowColor = Color.LTGRAY
    private var dx = 0F
    private var dy = 1.dp2px.toFloat()

    init {
//        val a = context.obtainStyledAttributes(attrs, R.styleable.CardView)
//        radius = a.getDimension(R.styleable.CardView_card_radius, 14.dp2px.toFloat())
//        shadowWidth = a.getDimension(R.styleable.CardView_card_shadow_width, 5.dp2px.toFloat()).toInt()
//        shadowRadius = a.getDimension(R.styleable.CardView_card_shadow_radius, 5.dp2px.toFloat())
//        shadowColor = a.getColor(R.styleable.CardView_card_shadow_color, Color.LTGRAY)
//        dx = a.getDimension(R.styleable.CardView_card_shadow_dx, 0F)
//        dy = a.getDimension(R.styleable.CardView_card_shadow_dy, 1.dp2px.toFloat())
//        a.recycle()

        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, width, height, radius)
            }
        }

        clipToOutline = true

        setPadding(paddingLeft + shadowWidth,
                paddingTop + shadowWidth,
                paddingRight + shadowWidth,
                paddingBottom + shadowWidth)

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.let {
            //画阴影
            it.drawRoundRect(shadowWidth.toFloat(), shadowWidth.toFloat(), width
                    .toFloat() - shadowWidth,
                    height.toFloat() - shadowWidth, radius, radius, shadowPaint)

            val sc = it.saveLayer(0F, 0F, width.toFloat(), height.toFloat(), null)

            //画出内容范围，SRC_IN方式，选出内容
            it.drawRoundRect(shadowWidth.toFloat(), shadowWidth.toFloat(), width
                    .toFloat() - shadowWidth,
                    height.toFloat() - shadowWidth, radius, radius, RoundPaint)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas2 = Canvas(bitmap)
            super.dispatchDraw(canvas2)

            //xfermode为SRC_IN
            RoundPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

            //画出内容
            it.drawBitmap(bitmap, 0F, 0F, RoundPaint)

            it.restoreToCount(sc)
            RoundPaint.xfermode = null
            bitmap.recycle()
        }
    }
}