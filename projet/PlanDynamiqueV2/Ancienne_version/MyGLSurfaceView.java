package com.example.import1;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Vue dans laquelle on dessine les graphiqueS avec OpenGL
 * Sert aussi a interfacer les intéractions avec l'utilisateur
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer renderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        // set the context
        setEGLContextClientVersion(3);
        //fix for error No Config chosen, but I don't know what this does.
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        // Setter du Renderer pour le dessiner dans la view
        renderer = new MyGLRenderer();
        setRenderer(renderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

}

