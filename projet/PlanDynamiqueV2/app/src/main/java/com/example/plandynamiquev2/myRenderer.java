package com.example.plandynamiquev2;
import java.util.ArrayList;
import java.util.Collections;
import android.content.Context;
import android.hardware.Sensor;
import android.opengl.GLES30;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.hardware.SensorManager;
import android.content.Context;
import android.hardware.Sensor ;
import android.hardware.SensorEvent ;
import android.hardware.SensorEventListener ;

public class myRenderer implements GLSurfaceView.Renderer {
    private  Trajet monTrajet;
    private int mWidth;
    private int mHeight;
    private static String TAG = "myRenderer";
    public Cube cube ;
    public Triangle triangle ;
    public Square square ;
    private static final float Z_NEAR = 1f;
    private static final float Z_FAR = 40f;



    //Model View Projection Matrix
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];

    public void construction(){
        /**
        monTrajet.addSommet(new Point(3,1));
        monTrajet.addSommet(new Point(3,2));
        monTrajet.addSommet(new Point(3,3));
        monTrajet.addSommet(new Point(3,4));
        monTrajet.addSommet(new Point(3,5));
        monTrajet.addSommet(new Point(3,6));
        monTrajet.addSommet(new Point(4,6));
         */


    }



    public myRenderer(Context context) {
        //cube can not be instianated here, because of "no egl context"  no clue.
        //do it in onSurfaceCreate and it is fine.  odd, but workable solution.
        monTrajet = new Trajet() ;
        construction();
    }


    public static int LoadShader(int type, String shaderSrc) {
        int shader;
        int[] compiled = new int[1];

        // Create the shader object
        shader = GLES30.glCreateShader(type);

        if (shader == 0) {
            return 0;
        }

        // Load the shader source
        GLES30.glShaderSource(shader, shaderSrc);

        // Compile the shader
        GLES30.glCompileShader(shader);

        // Check the compile status
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);

        if (compiled[0] == 0) {
            Log.e(TAG, "Erorr!!!!");
            Log.e(TAG, GLES30.glGetShaderInfoLog(shader));
            GLES30.glDeleteShader(shader);
            return 0;
        }

        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }


    // Initialize the shader and program object

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {


        //set the clear buffer color to light gray.
        //GLES30.glClearColor(0.9f, .9f, 0.9f, 0.9f);
        //set the clear buffer color to a dark grey.
        GLES30.glClearColor(0.1f, .1f, 0.1f, 0.9f);

        triangle = new Triangle();
        cube = new Cube();
        square = new Square();

    }

    // Draw the scene
    public void onDrawFrame(GL10 glUnused) {
        //Log.d(TAG, "drawing scene");
        int orientation=0;
        // Clear the color buffer  set above by glClearColor.
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        //need this otherwise, it will over right stuff and the cube will look wrong!
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 5f, 7f, -10f, 5f, -4f, 5f, 0f, 1.0f, 0.0f);
        // Création des rayons

        Matrix.setIdentityM(mRotationMatrix, 0); // Create a rotation and translation for the cube
        Matrix.translateM(mRotationMatrix, 0, 1.5f, 0.5f, 1f);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
        cube.draw(mMVPMatrix);
        Matrix.translateM(mRotationMatrix, 0, 3, 0, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        cube.draw(mMVPMatrix);
        Matrix.translateM(mRotationMatrix, 0, 3, 0, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        cube.draw(mMVPMatrix);
        Matrix.translateM(mRotationMatrix, 0, 0, 0, 7);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        cube.draw(mMVPMatrix);
        Matrix.translateM(mRotationMatrix, 0, -3, 0, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        cube.draw(mMVPMatrix);
        Matrix.translateM(mRotationMatrix, 0, -3, 0, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        cube.draw(mMVPMatrix);
        Matrix.setIdentityM(mRotationMatrix, 0);
        Matrix.translateM(mRotationMatrix, 0, 3f, 0f, 0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
        triangle.draw(mMVPMatrix);


        //première case = toujours l'entrée
        //Log.d(TAG, "size"+monTrajet.size());
        for (int i =1; i < monTrajet.size(); i++) {
            int x_1= monTrajet.get(i-1).getAbscisse();
            int x_2= monTrajet.get(i).getAbscisse();
            int z_1= monTrajet.get(i-1).getOrdonnee();
            int z_2= monTrajet.get(i).getOrdonnee();

            if (x_2-x_1 == 1) {
                //Log.d(TAG, "if (x_2-x_1 == 1)");
                switch (orientation){
                    case 3:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,1);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 3");
                        break;
                    case 2:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,  1.125f) ;
                        Matrix.rotateM(mRotationMatrix, 0, -90, 0, 1, 0);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 2");
                        break;
                    case 1:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,  1f) ;
                        Matrix.rotateM(mRotationMatrix, 0, 180, 0, 1, 0);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 0");
                        break;
                    case 0:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,  1.125f) ;
                        Matrix.rotateM(mRotationMatrix, 0, 90, 0, 1, 0);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 0");
                        break;
                }
                orientation=3;
            }
            else if (x_2-x_1 == -1){
                switch (orientation){
                    case 3:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,1);
                        Matrix.rotateM(mRotationMatrix, 0, 180, 0, 1, 0);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 3");
                        break;
                    case 2:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,1.125f);
                        Matrix.rotateM(mRotationMatrix, 0, 90, 0, 1, 0);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 2");
                        break;
                    case 1:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,  1f) ;
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 0");
                        break;
                    case 0:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,  1.125f) ;
                        Matrix.rotateM(mRotationMatrix, 0, -90, 0, 1, 0);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 0");
                        break;
                }
                orientation=1;

            }
            else if (z_2-z_1 == -1){
                switch (orientation){
                    case 3:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,1.125f);
                        Matrix.rotateM(mRotationMatrix, 0, 90, 0, 1, 0);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 3");
                        break;
                    case 2:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,1f);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 2");
                        break;
                    case 1:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,1.125f);
                        Matrix.rotateM(mRotationMatrix, 0, -90, 0, 1, 0);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 0");
                        break;
                    case 0:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,  1.125f) ;
                        Matrix.rotateM(mRotationMatrix, 0, 180, 0, 1, 0);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 0");
                        break;
                }
                orientation=2;

            }
            else {
                switch (orientation){
                    case 3:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,1.125f);
                        Matrix.rotateM(mRotationMatrix, 0, -90, 0, 1, 0);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 3");
                        break;
                    case 1:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,1.125f);
                        Matrix.rotateM(mRotationMatrix, 0, 90, 0, 1, 0);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 2");
                        break;
                    case 2:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,1.125f);
                        Matrix.rotateM(mRotationMatrix, 0, 180, 0, 1, 0);
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 0");
                        break;
                    case 0:
                        Matrix.translateM(mRotationMatrix, 0,0, 0,  1f) ;
                        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
                        square.draw(mMVPMatrix);
                        //Log.d(TAG, "case 0");
                        break;
                }
                orientation=0;
            }
        }
    }





    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        mWidth = width;
        mHeight = height;
        // Set the viewport
        GLES30.glViewport(0, 0, mWidth, mHeight);
        float aspect = (float) width / height;


        Matrix.perspectiveM(mProjectionMatrix, 0, 53.13f, aspect, Z_NEAR, Z_FAR);
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES30.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES30.GL_FRAGMENT_SHADER)
        int shader = GLES30.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);

        return shader;
    }


}