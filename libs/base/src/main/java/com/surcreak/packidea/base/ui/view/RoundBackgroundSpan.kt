package com.surcreak.packidea.base.ui.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import kotlin.math.abs

class RoundBackgroundSpan(val textColor: Int,
                          val backgroundColor: Int,
                          val textSize: Float,
                          val radius: Int,
                          val marginRight: Int): ReplacementSpan() {

    var textWidth = 0F

    override fun getSize(paint: Paint, text: CharSequence?, start: Int,
                         end: Int, fm: Paint.FontMetricsInt?): Int {
        if (text.isNullOrEmpty()) return 0
        return (paint.measureText(text, start, end) + 2*radius + marginRight).toInt()
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int,
                      end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        if (text.isNullOrEmpty()) return
        val defaultColor = paint.color

        canvas.save()
        paint.color = backgroundColor
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true

        val roundTextPaint = Paint(paint)
        roundTextPaint.color = textColor
        roundTextPaint.style = Paint.Style.FILL

        roundTextPaint.textSize = textSize
        textWidth = roundTextPaint.measureText(text, start, end) + 2*radius

        val offset = abs((roundTextPaint.fontMetrics.top - roundTextPaint.fontMetrics.bottom) -
                (paint.fontMetrics.top - paint.fontMetrics.bottom))/2

        val centerY = y + roundTextPaint.fontMetrics.top +
                (roundTextPaint.fontMetrics.bottom - roundTextPaint.fontMetrics.top)/2

        canvas.translate(0f, -offset)

        val rectF = RectF(x, centerY - textWidth/2,
            x + textWidth, centerY + textWidth/2)
        canvas.drawRoundRect(rectF, radius.toFloat(), radius.toFloat(), paint)

        canvas.drawText(text.toString(), start, end, x + radius, y.toFloat(), roundTextPaint)
        canvas.restore()

        paint.color = defaultColor
    }

}