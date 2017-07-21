package com.example.h.learn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;

import com.example.h.learn.R;

public class ShareActivity extends AppCompatActivity implements View.OnClickListener {

    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);



        //绑定点击事件
        findViewById(R.id.share_simple).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //
        getMenuInflater().inflate(R.menu.share_activity_actions, menu);

        MenuItem menuItem = menu.findItem(R.menu.share_activity_actions);

        //shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_simple:
                Log.i("info", "click share simple");
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "分享到"));
                break;
            case R.id.receive_share:
                break;
            case R.id.share_file:

                break;
            default:

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                Log.i("info", "click action share");
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
