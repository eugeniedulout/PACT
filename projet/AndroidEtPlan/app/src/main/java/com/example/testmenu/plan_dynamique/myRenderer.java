package com.example.testmenu.plan_dynamique;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.testmenu.algorithmie.PlusCourtChemin;
import com.example.testmenu.algorithmie.point.Point;
import com.example.testmenu.algorithmie.point.ProductPoint;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class myRenderer implements GLSurfaceView.Renderer {
    private Trajet monTrajet;
    private Trajet produits;
    private Trajet listPositions;
    private int mWidth;
    private int mHeight;
    private static String TAG = "myRenderer";
    public Cube cube ;
    public Triangle triangle ;
    public Products product;
    public Square square ;
    public Position position;
    private static final float Z_NEAR = 1f;
    private static final float Z_FAR = 40f;
    private ArrayList<Point> pointsProduits = new ArrayList<>();



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
        monTrajet.addSommet(new Point(3,7));
        monTrajet.addSommet(new Point(3,8));
        monTrajet.addSommet(new Point(3,9));
        monTrajet.addSommet(new Point(3,10));
        monTrajet.addSommet(new Point(3,11));
        monTrajet.addSommet(new Point(3,12));
        monTrajet.addSommet(new Point(3,13));
        monTrajet.addSommet(new Point(3,14));
        monTrajet.addSommet(new Point(4,14));
        monTrajet.addSommet(new Point(5,14));
        monTrajet.addSommet(new Point(6,14));
        monTrajet.addSommet(new Point(6,13));
        monTrajet.addSommet(new Point(6,12));
        monTrajet.addSommet(new Point(6,13));
        monTrajet.addSommet(new Point(6,14));
        monTrajet.addSommet(new Point(7,14));
        monTrajet.addSommet(new Point(8,14));
        monTrajet.addSommet(new Point(9,14));
        monTrajet.addSommet(new Point(9,13));
        monTrajet.addSommet(new Point(9,12));
        monTrajet.addSommet(new Point(9,11));
        monTrajet.addSommet(new Point(9,10));
        monTrajet.addSommet(new Point(9,9));
        monTrajet.addSommet(new Point(9,8));
        monTrajet.addSommet(new Point(9,7));
        monTrajet.addSommet(new Point(8,7));
        monTrajet.addSommet(new Point(7,7));
        monTrajet.addSommet(new Point(6,7));
        monTrajet.addSommet(new Point(6,6));
        monTrajet.addSommet(new Point(6,5));
        monTrajet.addSommet(new Point(6,4));
        monTrajet.addSommet(new Point(6,3));
        monTrajet.addSommet(new Point(6,2));
        monTrajet.addSommet(new Point(6,1));
         */


        // ICI la fonction qui renvoie les sommets du plus court chemin



    }


    //Cnstructor of the products and of the path
    public myRenderer(Context context) {






        pointsProduits=MainActivity2.pointsProduits;
        monTrajet = new Trajet();
        produits = new Trajet();
        ArrayList<Point> coordonee= PlusCourtChemin.getCoordonnesChemin(pointsProduits);
        monTrajet = new Trajet(coordonee);
        pointsProduits.remove(pointsProduits.size()-1);
        pointsProduits.remove(pointsProduits.size()-1);

        produits = new Trajet(pointsProduits);



        /**
        produits.addSommet(new ProductPoint(2,3));
        produits.addSommet(new ProductPoint(4,11));
        produits.addSommet(new ProductPoint(5,12));
        produits.addSommet(new ProductPoint(8,11));
        produits.addSommet(new ProductPoint(7,11));
        produits.addSommet(new ProductPoint(7,2));
         */

        //construction();




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
        product = new Products();
        position = new Position();

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
        Matrix.setLookAtM(mViewMatrix, 0, 1f, 6f, -10f, 4.5f, -4f, 5f, 0f, 1.0f, 0.0f);
        // Création des rayons


        /**
         * Code de l'affichage de la position (il faut la fonction qui renvoie les coordonnées
         */
        // x et z sont ici choisis arbitrairement, lors de l'intégration ils proviendront de la fonction
        // renvoyant les coordonnées réelles de l'utilisateur
        // listPosition.addSommet();
        double x= 3;
        double z=5;
        double z_cam1 = z-6;

        //code intégré
        //double x = getPosition().get(0);
        //double z = getPosition().get(1);
        //double z_cam1 = z-6;
        //listPositions.addSommet(new Point((int) x, (int) z));


        // affichage de la posiion
        // Reception des coordonnées en temps réels depuis le module de localisation
        //Puis on translate

        if (x<=1.5){
            //Matrix.setLookAtM(mViewMatrix, 0, 0, 2f, (float) z_cam1, 0, -0.6f, (float) z, 0f, 1.0f, 0.0f);
            Matrix.setIdentityM(mRotationMatrix, 0);
            Matrix.translateM(mRotationMatrix, 0,0, 0f, (float) z) ;
            Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
            position.draw(mMVPMatrix);

        }


        if (1.5<x && x<=4.5){
            //Matrix.setLookAtM(mViewMatrix, 0, 3, 2f, (float) z_cam1, 3, -0.6f, (float) z, 0f, 1.0f, 0.0f);
            Matrix.setIdentityM(mRotationMatrix, 0);
            Matrix.translateM(mRotationMatrix, 0,3, 0f, (float) z) ;
            Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
            position.draw(mMVPMatrix);

        }

        if (4.5<x && x<=7.5){
            //Matrix.setLookAtM(mViewMatrix, 0, 6, 2f, (float) z_cam1, 6, -0.6f, (float) z, 0f, 1.0f, 0.0f);
            Matrix.setIdentityM(mRotationMatrix, 0);
            Matrix.translateM(mRotationMatrix, 0,6, 0f, (float) z) ;
            Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
            position.draw(mMVPMatrix);

        }
        if (7.5<x){
            //Matrix.setLookAtM(mViewMatrix, 0, 9, 2f, (float) z_cam1, 9, -0.6f, (float) z, 0f, 1.0f, 0.0f);
            Matrix.setIdentityM(mRotationMatrix, 0);
            Matrix.translateM(mRotationMatrix, 0,9, 0f, (float) z) ;
            Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
            position.draw(mMVPMatrix);

        }


        Matrix.setIdentityM(mRotationMatrix, 0);
        Matrix.translateM(mRotationMatrix, 0, 6f, 0f, 0.3f);
        Matrix.rotateM(mRotationMatrix, 0, 180, 0, 1, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
        triangle.draw(mMVPMatrix);


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
            double x_1= monTrajet.get(i-1).getX();
            double x_2= monTrajet.get(i).getX();
            double z_1= monTrajet.get(i-1).getY();
            double z_2= monTrajet.get(i).getY();

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
            else if (z_2-z_1 == 1){
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

        for (int i =0; i < produits.size(); i++) {

            double xp= produits.get(i).getX();
            double zp= produits.get(i).getY();


            Matrix.setIdentityM(mRotationMatrix, 0);
            Matrix.translateM(mRotationMatrix, 0,(float)xp, 0.5f,  (float)zp) ;
            Matrix.scaleM(mRotationMatrix,0,0.3f,0.3f,0.3f);
            //Matrix.rotateM(mRotationMatrix, 0, 90, 0, 0, 1);
            Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mRotationMatrix, 0);// combine the model with the view matrix
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);  // combine the model-view with the projection matrix
            product.draw(mMVPMatrix);
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

    private class calculPlusCourtChemin extends AsyncTask<Integer,Integer,String> {

        @Override
        protected String doInBackground(Integer... integers) {

            //coordonnesPlusCourtChemin = PlusCourtChemin.getCoordonnesChemin(pointsProduits);


            return "finished";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }



}