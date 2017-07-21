package com.simon.androidhint;

import android.app.Activity;
import android.os.Bundle;

import com.simon.statusbar.StatusBarControl;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarControl.setStatusColor(this, getResources().getColor(android.R.color.holo_red_dark));
    }
}
