package com.digital.gnsbook;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import com.httpgnsbook.gnsbook.R;

public class DividerDecorator extends ItemDecoration {
    private Drawable mDivider;

    public DividerDecorator(Context context) {
        this.mDivider = context.getResources().getDrawable(R.drawable.recyclerview_divider);
    }

    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        int  pad = recyclerView.getPaddingLeft();
        int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            int bottom = childAt.getBottom() + ((LayoutParams) childAt.getLayoutParams()).bottomMargin;
            this.mDivider.setBounds(pad, bottom, width, this.mDivider.getIntrinsicHeight() + bottom);
            this.mDivider.draw(canvas);
        }
    }
}
