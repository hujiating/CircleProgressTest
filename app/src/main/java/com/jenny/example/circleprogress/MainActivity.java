package com.jenny.example.circleprogress;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private ArcProgress arcProgress;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arcProgress = (ArcProgress) findViewById(R.id.arc_progress);
    }

    public void click(View view){
        if(i<6){
            arcProgress.setGrade(i,++i);
        }else
            i=0;
    }
}
