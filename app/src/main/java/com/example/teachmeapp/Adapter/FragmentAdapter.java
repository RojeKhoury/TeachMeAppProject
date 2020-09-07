package com.example.teachmeapp.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.teachmeapp.Fragments.FirstFragment;
import com.example.teachmeapp.Fragments.MapsFragment;

import javax.annotation.Nullable;

public class FragmentAdapter extends FragmentPagerAdapter {
    Context context;

    public FragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0)
            return MapsFragment.getINSTANCE();
        if (position == 1) {
            return FirstFragment.getINSTANCE();
        }

        else
            return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Maps Fragment";
            case 1:
                return "First Fragment";
            default:
                return "invalid";
        }
    }
}
