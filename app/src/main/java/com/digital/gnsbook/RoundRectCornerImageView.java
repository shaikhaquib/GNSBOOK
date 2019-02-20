package com.digital.gnsbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint({"AppCompatCustomView"})
public class RoundRectCornerImageView extends ImageView {
    public static float radius = 8.0f;

    public RoundRectCornerImageView(Context context) {
        super(context);
    }

    public RoundRectCornerImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RoundRectCornerImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.addRoundRect(new RectF(0.0f, 0.0f, (float) getWidth(), (float) getHeight()), radius, radius, Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
