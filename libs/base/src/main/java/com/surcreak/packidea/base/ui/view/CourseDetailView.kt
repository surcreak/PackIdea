package com.surcreak.packidea.base.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.surcreak.packidea.base.extensions.dp2px

class CourseDetailView @JvmOverloads constructor(context: Context,
                                                 attrs: AttributeSet? = null,
                                                 defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    var name: String = ""
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    @ColorInt
    var nameColor: Int = Color.YELLOW
        set(value) {
            field = value
            invalidate()
        }

    var numStr: String = ""
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    @ColorInt
    var numStrColor: Int = Color.BLUE
        set(value) {
            field = value
            invalidate()
        }

    var radius: Float = 0F
    var textSize: Float = 0F
    val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
    val textPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = this@CourseDetailView.textSize
            color = Color.WHITE
        }
    }

    val rectFPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

//    init {
//        val a = context.obtainStyledAttributes(attrs, R.styleable.CourseDetailView)
//        textSize = a.getDimensionPixelSize(R.styleable.CourseDetailView_textSize,
//                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
//                        12f, resources.displayMetrics).toInt()).toFloat()
//        radius = a.getDimension(R.styleable.CourseDetailView_radius, 5.dp2px.toFloat())
//        a.recycle()
//    }

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

        when (specMode) {
            MeasureSpec.AT_MOST -> {
                width = textPaint.measureText(name).toInt() +
                        textPaint.measureText(numStr).toInt() +
                        paddingLeft + paddingRight +
                        4*5.dp2px
            }
            MeasureSpec.EXACTLY -> {
                width = specSize
            }
            MeasureSpec.UNSPECIFIED -> {
                width = width.coerceAtLeast(specSize)
            }
        }
        return width
    }

    private fun measureHeight(defaultHeight: Int, measureSpec: Int): Int {
        var height = defaultHeight

        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        when (specMode) {
            MeasureSpec.AT_MOST -> {
                height = (-textPaint.ascent() + textPaint.descent()).toInt() +
                        paddingTop + paddingBottom +
                        2.dp2px
            }
            MeasureSpec.EXACTLY -> {
                height = specSize
            }
            MeasureSpec.UNSPECIFIED -> {
                height = height.coerceAtLeast(specSize)
            }
        }
        return height
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            val save = it.saveLayer(
                    paddingLeft.toFloat(),
                    paddingTop.toFloat(),
                    (width - paddingRight).toFloat(),
                    (height - paddingBottom).toFloat(),
                    rectFPaint)

            rectFPaint.color = nameColor
            it.drawRoundRect(
                    paddingLeft.toFloat(),
                    paddingTop.toFloat(),
                    (width - paddingRight).toFloat(),
                    (height - paddingBottom).toFloat(),
                    radius,
                    radius,
                    rectFPaint)

            rectFPaint.xfermode = xfermode
            rectFPaint.color = numStrColor
            val nameWith = paddingLeft.toFloat() + textPaint.measureText(name).toInt() + 2*5.dp2px
            it.drawRect(
                    nameWith,
                    paddingTop.toFloat(),
                    (width - paddingRight).toFloat(),
                    (height - paddingBottom).toFloat(),
                    rectFPaint)
            rectFPaint.xfermode = null
            it.restoreToCount(save)
            val baseline = ((height-paddingBottom + paddingTop -
                    textPaint.fontMetricsInt.bottom - textPaint.fontMetricsInt.top) / 2).toFloat()
            textPaint.textAlign = Paint.Align.CENTER
            textPaint.color = Color.WHITE
            it.drawText(name, nameWith/2, baseline, textPaint)
            val numWith = textPaint.measureText(numStr).toInt() + 2*5.dp2px
            textPaint.color = nameColor
            it.drawText(numStr, nameWith + numWith/2, baseline, textPaint)
        }
    }
}