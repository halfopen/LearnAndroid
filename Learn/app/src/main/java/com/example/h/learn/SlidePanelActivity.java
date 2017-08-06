package com.example.h.learn;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.h.learn.fragment.BlankFragment1;
import com.example.h.learn.fragment.BlankFragment2;


public class SlidePanelActivity extends AppCompatActivity implements BlankFragment2.OnFragmentInteractionListener,BlankFragment1.OnFragmentInteractionListener{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_panel);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
