package com.digital.gnsbook.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.httpgnsbook.gnsbook.R;

public class Frg_Home extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.new_homefrg, container, false);

        Fragment frg_suggestiom = new Frg_Suggestion();
        Fragment frg_wallpost = new Frg_WallPost();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.frgfriendSuggestion, frg_suggestiom ).commit();
        FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();

        transaction1.add(R.id.frgwallPost, frg_wallpost ).commit();

       /* if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener(new Frg_WallPost.C09321());
        }*/

        return view;
    }




}
