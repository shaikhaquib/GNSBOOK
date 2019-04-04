package com.digital.gnsbook.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import java.util.List;

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public FragmentViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> list, List<String> list2) {
        super(fragmentManager);
        this.fragments = list;
        this.titles = list2;
    }

    public Fragment getItem(int i) {
        return (Fragment) this.fragments.get(i);
    }

    public int getCount() {
        return this.titles.size();
    }

    public CharSequence getPageTitle(int i) {
        return (CharSequence) this.titles.get(i);
    }

    public void setPrimaryItem(@NonNull View view, int i, @NonNull Object obj) {
        super.setPrimaryItem(view, i, obj);
    }
}
