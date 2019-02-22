package com.digital.gnsbook.Config;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

public abstract class PaginationScrollListener extends OnScrollListener {
    private static final int PAGE_SIZE = 20;
    LinearLayoutManager layoutManager;

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

    protected abstract void loadMoreItems();

    public PaginationScrollListener(LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }

    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        super.onScrolled(recyclerView, i, i2);
        int count = this.layoutManager.getChildCount();
        i = this.layoutManager.getItemCount();
        i2 = this.layoutManager.findFirstVisibleItemPosition();
        if (!isLoading() && !isLastPage() && count + i2 >= i && i2 >= 0 && i >= 20) {
            loadMoreItems();
        }
    }
}
