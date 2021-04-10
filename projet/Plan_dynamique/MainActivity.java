package com.example.import1;


import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {


    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creation de la GLSurfaceView comme vue de cette activité
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }


}