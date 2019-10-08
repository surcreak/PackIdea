package com.surcreak.packidea.base.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.surcreak.packidea.base.extensions.dp2px


class ImageView @JvmOverloads constructor(context: Context,
                                          attrs: AttributeSet? = null,
                                          defStyleAttr: Int = 0)
    : AppCompatImageView(context, attrs, defStyleAttr) {

    private val paint by lazy {
        Paint().apply {
            color = Color.WHITE
            isAntiAlias = true
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        }
    }

    private var roundTopLeft = 5.dp2px.toFloat()
    private var roundTopRight = 5.dp2px.toFloat()
    private var roundBottomLeft = 5.dp2px.toFloat()
    private var roundBottomRight = 5.dp2px.toFloat()
    private var round = 12.dp2px.toFloat()

    init {
//        val a = context.obtainStyledAttributes(attrs, R.styleable.ImageView)
//        round = a.getDimension(R.styleable.ImageView_img_radius, 12.dp2px.toFloat())
//        if (round == 0F) {
//            roundTopLeft = a.getDimension(R.styleable.ImageView_img_radius, 5.dp2px.toFloat())
//            roundTopRight = a.getDimension(R.styleable.ImageView_img_radius, 5.dp2px.toFloat())
//            roundBottomLeft = a.getDimension(R.styleable.ImageView_img_radius, 5.dp2px.toFloat())
//            roundBottomRight = a.getDimension(R.styleable.ImageView_img_radius, 5.dp2px.toFloat())
//        } else {
//            roundTopLeft = round
//            roundTopRight = round
//            roundBottomLeft = round
//            roundBottomRight = round
//        }
//        a.recycle()
    }

    override fun draw(canvas: Canvas) {
        val bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888)
        val canvas2 = Canvas(bitmap)
        val sc = canvas2.saveLayer(0F, 0F, width.toFloat(), height.toFloat(), null)
        super.draw(canvas2)

        drawLiftUp(canvas2)
        drawLiftDown(canvas2)
        drawRightUp(canvas2)
        drawRightDown(canvas2)

        canvas2.restoreToCount(sc)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        bitmap.recycle()
    }

    private fun drawLiftUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, roundTopLeft)
        path.lineTo(0f, 0f)
        path.lineTo(roundTopLeft, 0f)
        path.arcTo(RectF(0f, 0f, roundTopLeft * 2,
                roundTopLeft * 2), -90f, -90f)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawLiftDown(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, (height - roundBottomLeft))
        path.lineTo(0f, height.toFloat())
        path.lineTo(roundBottomLeft, height.toFloat())
        path.arcTo(RectF(0f, (height - roundBottomLeft * 2), (roundBottomLeft * 2),
                height.toFloat()), 90f, 90f)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawRightDown(canvas: Canvas) {
        val path = Path()
        path.moveTo((width - roundBottomRight), height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height - roundBottomRight)
        path.arcTo(RectF((width - roundBottomRight * 2), (height - roundBottomRight * 2),
                width.toFloat(), height.toFloat()), -0f, 90f)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawRightUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(width.toFloat(), roundTopRight)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo((width - roundTopRight), 0f)
        path.arcTo(RectF((width - roundTopRight * 2), 0f, width.toFloat(),
                (0 + roundTopRight * 2)), -90f, 90f)
        path.close()
        canvas.drawPath(path, paint)
    }

}