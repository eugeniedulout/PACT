package com.example.testmenu.plan_dynamique;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class myGlSurfaceView extends GLSurfaceView {

    myRenderer myRender;

    public myGlSurfaceView(Context context) {
        super(context);
        // Create acontext
        setEGLContextClientVersion(2);

        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        // Set the Renderer for drawing on the GLSurfaceView
        myRender = new myRenderer(context);
        setRenderer(myRender);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


}