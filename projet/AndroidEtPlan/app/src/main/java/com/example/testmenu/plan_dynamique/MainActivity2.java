package com.example.testmenu.plan_dynamique;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.testmenu.R;
import com.example.testmenu.algorithmie.PlusCourtChemin;
import com.example.testmenu.algorithmie.point.Point;
import com.example.testmenu.fragments.TestPlan;

import java.util.ArrayList;

public class MainActivity2 extends Activity {


    public static ArrayList<Point> pointsProduits = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pointsProduits = (ArrayList<Point>) getIntent().getSerializableExtra("produits coordonnees");

        if (detectOpenGLES30()) { // gestion de la compatibilité de OpenGL ES
            setContentView(new myGlSurfaceView(this));
        } else {
            Log.e("openglcube", "OpenGL ES 3.0 not supported on device.  Exiting...");
            finish();
        }
    }

    private boolean detectOpenGLES30() {
        ActivityManager am =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x30000);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }



}
