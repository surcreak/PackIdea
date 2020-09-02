package com.surcreak.packidea.homepage.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ForegroundFrameLayout extends FrameLayout {
    private Path mPath;
    private Paint mPaint;
    private Paint mPaintForeStroke;
    private RectF mRectF;
    private RectF mStrokeRectF;
    private float mRadius;
    private boolean isClipBackground;
    private float foreStrokeWidth;
    private int foreStrokeColor;
    private boolean isEffect = true;

    public ForegroundFrameLayout(@NonNull Context context) {
        this(context, null);
    }
    public ForegroundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public ForegroundFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ForegroundFrameLayout);
//        mRadius = ta.getDimension(R.styleable.ForegroundFrameLayout_rlRadius, 10);
//        isClipBackground = ta.getBoolean(R.styleable.ForegroundFrameLayout_rlClipBackground, true);
//        foreStrokeWidth = ta.getDimension(R.styleable.ForegroundFrameLayout_foreStrokeWidth, 20F);
//        foreStrokeColor = ta.getColor(R.styleable.ForegroundFrameLayout_foreStrokeColor, Color.BLUE);
//        ta.recycle();

        mRadius = 10F;
        isClipBackground = true;
        foreStrokeWidth = 20F;
        foreStrokeColor = Color.BLUE;

        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectF = new RectF();
        mStrokeRectF = new RectF();

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mPaintForeStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintForeStroke.setStyle(Paint.Style.STROKE);
        mPaintForeStroke.setStrokeWidth(foreStrokeWidth);
        mPaintForeStroke.setColor(foreStrokeColor);
    }
    public void setRadius(float radius) {
        mRadius = radius;
        postInvalidate();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.set(0, 0, w, h);
        mStrokeRectF.set(10, 10, w -10, h -10);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        if (isEffect) {
//            if (Build.VERSION.SDK_INT >= 28) {
            draw28(canvas);
//            } else {
//                draw27(canvas);
//            }
        } else {
            super.draw(canvas);
        }
    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (isEffect) {
//            if (Build.VERSION.SDK_INT >= 28) {
                dispatchDraw28(canvas);
//            } else {
//                dispatchDraw27(canvas);
//            }
        } else {
            super.dispatchDraw(canvas);
        }
    }

    private void draw27(Canvas canvas) {
        if (isClipBackground) {
            canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG);
            super.draw(canvas);
            canvas.drawPath(genPath(), mPaint);
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }

    private void draw28(Canvas canvas) {
        if (isClipBackground) {
            canvas.save();
            canvas.clipPath(genPath());
            super.draw(canvas);
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }

    private void dispatchDraw27(Canvas canvas) {
        canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        //canvas.drawPath(genPath(), mPaintForeStroke);
        canvas.restore();
    }

    private void dispatchDraw28(Canvas canvas) {
        canvas.save();
        canvas.clipPath(genPath());
        super.dispatchDraw(canvas);
        //canvas.drawPath(genPath(), mPaintForeStroke);
        canvas.restore();
    }

    private Path genPath() {
        mPath.reset();
        mPath.addRoundRect(mRectF, mRadius, mRadius, Path.Direction.CW);
        return mPath;
    }
    
    public void setIsEffect(Boolean isEffect) {
        this.isEffect = isEffect;
        requestLayout();
    }
}