package com.example.plandynamiquev2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class myGlSurfaceView extends GLSurfaceView {

    myRenderer myRender;

    public myGlSurfaceView(Context context) {
        super(context);
        // Create acontext
        setEGLContextClientVersion(3);

        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        // Set the Renderer for drawing on the GLSurfaceView
        myRender = new myRenderer(context);
        setRenderer(myRender);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


}