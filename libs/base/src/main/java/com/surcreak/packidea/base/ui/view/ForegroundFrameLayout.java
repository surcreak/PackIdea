package com.surcreak.packidea.base.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.surcreak.packidea.base.R;

public class ForegroundFrameLayout extends FrameLayout {
    private Path mPath;
    private Paint mPaint;
    private Paint mPaintForeStroke;
    private RectF mRectF;
    private RectF mStrokeRectF;
    private float mRadius;
    private float foreStrokeWidth;
    private int foreStrokeColor;
    private boolean isEffect = true;
    private Xfermode xfermode;
    private Bitmap roundRectBitmap;

    public ForegroundFrameLayout(@NonNull Context context) {
        this(context, null);
    }
    public ForegroundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public ForegroundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ForegroundFrameLayout);
        mRadius = ta.getDimension(R.styleable.ForegroundFrameLayout_rlRadius, 50);
        foreStrokeWidth = ta.getDimension(R.styleable.ForegroundFrameLayout_foreStrokeWidth, 20F);
        foreStrokeColor = ta.getColor(R.styleable.ForegroundFrameLayout_foreStrokeColor, Color.BLUE);
        ta.recycle();

        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectF = new RectF();
        mStrokeRectF = new RectF();

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        mPaintForeStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintForeStroke.setStyle(Paint.Style.STROKE);
        mPaintForeStroke.setStrokeWidth(foreStrokeWidth);
        mPaintForeStroke.setColor(foreStrokeColor);
        if (getBackground() == null) {
            setBackgroundColor(Color.GRAY);
        }

        //有些机器子view中有webview，导致effectDraw会闪屏可以使用Outline.
//        setOutlineProvider(new RoundRectOutlineProvider(mRadius));
//        setClipToOutline(true);
    }

    /**
     * 设置圆角半径
     * @param radius
     */
    public void setRadius(float radius) {
        mRadius = radius;
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.set(0, 0, w, h);
        mStrokeRectF.set(10, 10, w -10, h -10);
        roundRectBitmap = makeRoundRect(w, h);
    }

    /**
     * 获取圆角 bitmap， 之前因为直接画圆角矩形，导致 PorterDuff 不生效。
     * 原因是直接绘制圆角矩形其 src 的范围就只有圆角矩形的区域，用bitmap后就解决。
     * @param w
     * @param h
     * @return
     */
    private Bitmap makeRoundRect(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        mPaint.setColor(Color.YELLOW);
        c.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
        return bm;
    }

    private Bitmap makeRect(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        mPaint.setColor(Color.WHITE);
        c.drawRect(mRectF, mPaint);
        return bm;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isEffect) {
            effectDraw(canvas);
        } else {
            super.draw(canvas);
        }
    }

    /**
     *  整体变为圆角，包括背景。并在内容上画上边框。只有当有背景的时候才会调用。
     *  没有锯齿
     * @param canvas
     */
    private void effectDraw(Canvas canvas) {
        int layerId =  canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.drawPath(genPath(), mPaintForeStroke);
        mPaint.setXfermode(xfermode);
        canvas.drawBitmap(roundRectBitmap,0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    /**
     *  整体变为圆角，包括背景。并在内容上画上边框。只有当有背景的时候才会调用。
     *  但是有锯齿
     * @param canvas
     */
    @Deprecated
    private void oldDraw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(genPath());
        super.draw(canvas);
        canvas.drawPath(genPath(), mPaintForeStroke);
        canvas.restore();
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        canvas.drawPath(genPath(), mPaintForeStroke);
    }

    /**
     * 获取圆角路径
     * @return
     */
    private Path genPath() {
        mPath.reset();
        mPath.addRoundRect(mRectF, mRadius, mRadius, Path.Direction.CW);
        return mPath;
    }

    /**
     *  设置是否作用
     * @param isEffect
     */
    public void setIsEffect(Boolean isEffect) {
        this.isEffect = isEffect;
        requestLayout();
    }
}