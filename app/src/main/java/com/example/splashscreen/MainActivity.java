package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //variables for back button pressed check
    private static final int TIME_TO_PRESS_BACK = 2000;
    private long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //change notification bar color back to black
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
    }

    //press back twice to close the app
    @Override
    public void onBackPressed(){
        if(TIME_TO_PRESS_BACK + backPressed > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(getApplicationContext(), "Press Back again to Exit!",
                    Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}