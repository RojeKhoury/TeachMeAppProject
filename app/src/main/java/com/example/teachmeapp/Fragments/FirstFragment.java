package com.example.teachmeapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teachmeapp.R;

import javax.annotation.Nullable;

public class FirstFragment extends Fragment {

    private static FirstFragment INSTANCE = null;

    View view;

    public FirstFragment() {
    }

    public static FirstFragment getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new FirstFragment();
        return INSTANCE;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.firstfragment, container, false);
        return view;
    }
}
