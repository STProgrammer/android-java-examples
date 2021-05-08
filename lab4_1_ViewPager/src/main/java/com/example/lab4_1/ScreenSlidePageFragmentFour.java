package com.example.lab4_1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ScreenSlidePageFragmentFour extends Fragment {

    TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        mTextView = (TextView) view.findViewById(R.id.text);
        view.setBackgroundColor(Color.YELLOW);
        mTextView.setText(String.valueOf(4));
        return view;
    }

}