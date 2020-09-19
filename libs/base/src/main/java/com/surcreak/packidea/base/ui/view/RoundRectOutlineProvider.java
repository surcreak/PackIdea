package com.surcreak.packidea.base.ui.view;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

class RoundRectOutlineProvider extends ViewOutlineProvider {

    private float mRadius;

    RoundRectOutlineProvider(float radius) {
        this.mRadius = radius;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), mRadius);
    }
}