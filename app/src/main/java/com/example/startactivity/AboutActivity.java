package com.example.startactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public  class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

    }
    public void OnBack(View view) {
       AboutActivity.this.finish();
    }
}// AboutActivity类尾部

