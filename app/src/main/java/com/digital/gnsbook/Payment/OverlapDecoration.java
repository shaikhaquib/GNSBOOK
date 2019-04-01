package com.digital.gnsbook.Payment;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class OverlapDecoration extends ItemDecoration {
    private static final int vertOverlap = -20;

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
        if (recyclerView.getChildAdapterPosition(view) == state.getItemCount() - 1) {
            rect.setEmpty();
        } else {
            rect.set(0, 0, -20, 0);
        }
    }
}
