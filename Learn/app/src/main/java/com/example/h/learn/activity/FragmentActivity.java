package com.example.h.learn.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.h.learn.R;
import com.example.h.learn.fragment.BlankFragment1;
import com.example.h.learn.fragment.BlankFragment2;

public class FragmentActivity extends AppCompatActivity  implements View.OnClickListener, BlankFragment1.OnFragmentInteractionListener, BlankFragment2.OnFragmentInteractionListener{


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        int[] ids = new int[]{R.id.btn_fragment1, R.id.btn_fragment2};

        for (int id:ids
             ) {
            findViewById(id).setOnClickListener(this);
        }

        if(findViewById(R.id.fragment_layout)!=null){

            //
            if(savedInstanceState !=null){
                return;
            }

            BlankFragment1 itemFragment = new BlankFragment1();
            //传参数
            itemFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_layout, itemFragment).commit();
        }
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.btn_fragment1:


                transaction.replace(R.id.fragment_layout, new BlankFragment1());
                transaction.addToBackStack(null);
                transaction.commit();

                break;
            case R.id.btn_fragment2:
                transaction.replace(R.id.fragment_layout, new BlankFragment2());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            default:
        }
    }
}
