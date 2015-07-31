package com.hnb.hello.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.hnb.hello.R;
import com.hnb.hello.base.BaseActivity;

// Flash screen activity, just to say hello
public class FlashActivity extends BaseActivity
{

    Button btnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        initView();
    }


    // init View, map the view object to xml layout
    private void initView()
    {
        btnStart = (Button) findViewById(R.id.btnStart);
    }


    // when user click on 'Get Started' button
    public void onButtonStartClick(View v)
    {
        // intent to Chat activity
        Intent intent = new Intent(FlashActivity.this, ChatActivity.class);
        startActivity(intent);
        finish();
    }

}
