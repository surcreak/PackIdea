package com.surcreak.packidea.base.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.surcreak.packidea.base.extensions.dp2px

class CircleNodeProgress @JvmOverloads constructor(context: Context,
                                                   attrs: AttributeSet? = null,
                                                   defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private val arrivedPaint by lazy {
        Paint().apply {
            color = Color.BLUE
            isAntiAlias = true
        }
    }

    private val noArrivedPaint by lazy {
        Paint().apply {
            color = Color.GRAY
            isAntiAlias = true
        }
    }

    val bitmapPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            isAntiAlias = true
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        }
    }

    var totalNode:Int = 0
        set(value) {
            field = value
            invalidate()
        }

    private var diameter = 6F
    private var linkLength = 0F

    @ColorInt
    var notArrivedColor: Int = Color.parseColor("#ECEFF5")
        set(value) {
            field = value
            invalidate()
        }

    @ColorInt
    var arrivedColor: Int = Color.parseColor("#8CD893")
        set(value) {
            field = value
            invalidate()
        }

    var arrivedBitmap: Bitmap? = null

    var arrivedNode: Int = 0
        set(value) {
            if (value > totalNode) {
                throw IllegalArgumentException("arrivedNode($arrivedNode) " +
                        "cannot be greater than totalNode($totalNode)")
            }
            field = value
            invalidate()
        }

    var dstRectF = RectF(0F, 0F, 0F, 0F)

    init {
//        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleNodeProgress)
        diameter = 20.dp2px.toFloat()
//        arrivedBitmap = BitmapFactory.decodeResource(context.resources, arrivedResID)
//        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measureWidth(suggestedMinimumWidth, widthMeasureSpec)
        val height = measureHeight(suggestedMinimumHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun measureWidth(defaultWidth: Int, measureSpec: Int): Int {
        var width = defaultWidth

        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        linkLength = if (linkLength == 0F) (diameter * 34 /20) else linkLength

        when (specMode) {
            MeasureSpec.AT_MOST -> {
                width = (diameter * totalNode + (totalNode - 1) * linkLength).toInt()
                if (width > specSize) {
                    linkLength = (specSize - diameter * totalNode) / (totalNode -1)
                }
            }
            MeasureSpec.UNSPECIFIED -> {
                width = (diameter * totalNode + (totalNode - 1) * linkLength).toInt()
            }
            MeasureSpec.EXACTLY -> {
                width = specSize
            }
        }
        return width
    }

    private fun measureHeight(defaultHeight: Int, measureSpec: Int): Int {
        var height = defaultHeight

        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        when (specMode) {
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                height = diameter.toInt()
            }
            MeasureSpec.EXACTLY -> {
                height = specSize
            }
        }
        return height
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            var x = 0F
            val arcHeight = diameter * 8 / 20
            val arcStartY = (diameter - arcHeight) / 2
            //接缝
            val joint = 0.04F * diameter

            if (totalNode >= 2) {
                it.drawRect(diameter/2,
                    arcHeight,
                    (diameter + linkLength) * (totalNode - 1),
                    arcStartY + arcHeight,
                    noArrivedPaint)
            }

            for (i in 0 until totalNode) {
                it.drawCircle(x + diameter/2, diameter/2, diameter/2, noArrivedPaint)
                x += (linkLength + diameter - joint)
            }

            if (arrivedNode >= 2) {
                canvas.drawRect(diameter/2,
                    arcHeight,
                    diameter * (arrivedNode - 1) + linkLength * (arrivedNode - 1),
                    arcStartY + arcHeight,
                    arrivedPaint)
            }

            x = 0F
            for (i in 0 until arrivedNode) {
                it.drawCircle(x + diameter/2, diameter/2, diameter/2, arrivedPaint)
                arrivedBitmap?.let { bitmap ->
                    dstRectF.apply {
                        left = x
                        top = 0F
                        right = x + diameter
                        bottom = height.toFloat()
                    }
                    it.drawBitmap(bitmap, null, dstRectF, bitmapPaint)
                }
                x += (linkLength + diameter - joint)
            }
        }
    }

}