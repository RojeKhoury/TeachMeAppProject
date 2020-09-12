package com.example.teachmeapp.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.teachmeapp.Fragments.MapsFragment;
import com.example.teachmeapp.Fragments.MapsFragmentChooseLocation;
import com.example.teachmeapp.Helpers.Globals;

import javax.annotation.Nullable;

public class FragmentAdapter extends FragmentPagerAdapter {
    Context context;
    int m_fragmentNumber;

    public FragmentAdapter(FragmentManager fm, Context context, int fragmentNumber) {
        super(fm);
        this.context = context;
        this.m_fragmentNumber = fragmentNumber;
    }

    @Override
    public Fragment getItem(int position) {
        switch (m_fragmentNumber) {
            case Globals.MAPS_CHOOSE_LOCATION: {
                return MapsFragmentChooseLocation.getINSTANCE();
            }
            case Globals.MAPS_REGULAR: {
                return MapsFragment.getINSTANCE();
            }
        }
        return null;
    }
    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Maps Fragment";
    }
}
