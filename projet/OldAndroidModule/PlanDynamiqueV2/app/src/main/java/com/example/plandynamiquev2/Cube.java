package com.example.plandynamiquev2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES30;
import android.util.Log;

public class Cube {
    private int mProgramObject;
    private int mMVPMatrixHandle;
    private int mColorHandle;
    private FloatBuffer mVertices;


    float size = 0.5f;

    // creation du cube
    float[] mVerticesData = new float[]{
            ////////////////////////////////////////////////////////////////////
            // Devant (face inversée)
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            -size, size, 10*size, // top-left
            -size, -size, 10*size, // bottom-left
            size, -size, 10*size, // bottom-right
            // Triangle 2
            size, -size, 10*size, // bottom-right
            size, size, 10*size, // top-right
            -size, size, 10*size, // top-left
            ////////////////////////////////////////////////////////////////////
            // Derrière
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            -size, size, 0, // top-left
            -size, -size, 0, // bottom-left
            size, -size, 0, // bottom-right
            // Triangle 2
            size, -size, 0, // bottom-right
            size, size, 0, // top-right
            -size, size, 0, // top-left

            ////////////////////////////////////////////////////////////////////
            // Gauche
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            -size, size, 0, // top-left
            -size, -size, 0, // bottom-left
            -size, -size, 10*size, // bottom-right
            // Triangle 2
            -size, -size, 10*size, // bottom-right
            -size, size, 10*size, // top-right
            -size, size, 0, // top-left
            ////////////////////////////////////////////////////////////////////
            // Droite
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            size, size, 0, // top-left
            size, -size, 0, // bottom-left
            size, -size, 10*size, // bottom-right
            // Triangle 2
            size, -size, 10*size, // bottom-right
            size, size, 10*size, // top-right
            size, size, 0, // top-left

            ////////////////////////////////////////////////////////////////////
            // Haut
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            -size, size, 0, // top-left
            -size, size, 10*size, // bottom-left
            size, size, 10*size, // bottom-right
            // Triangle 2
            size, size, 10*size, // bottom-right
            size, size, 0, // top-right
            -size, size, 0, // top-left
            ////////////////////////////////////////////////////////////////////
            // Bas
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            -size, -size, 0, // top-left
            -size, -size, 10*size, // bottom-left
            size, -size, 10*size, // bottom-right
            // Triangle 2
            size, -size, 10*size, // bottom-right
            size, -size, 0, // top-right
            -size, -size, 0 // top-left
    };

    float color1[] = {0.2f, 0.7098f, 0.8980f, 1.0f};
    float color2[] = {0.6f, 0.98f, 0.230f, 1.0f};
    float color3[] = {0.2f, 0.78f, 0.580f, 1.0f};

    //vertex shader code
    String vShaderStr =
            "#version 300 es 			  \n"
                    + "uniform mat4 uMVPMatrix;     \n"
                    + "in vec4 vPosition;           \n"
                    + "void main()                  \n"
                    + "{                            \n"
                    + "   gl_Position = uMVPMatrix * vPosition;  \n"
                    + "}                            \n";
    //fragment shader code.
    String fShaderStr =
            "#version 300 es		 			          	\n"
                    + "precision mediump float;					  	\n"
                    + "uniform vec4 vColor;	 			 		  	\n"
                    + "out vec4 fragColor;	 			 		  	\n"
                    + "void main()                                  \n"
                    + "{                                            \n"
                    + "  fragColor = vColor;                    	\n"
                    + "}                                            \n";

    String TAG = "Cube";



    //constructor
    public Cube() {
        //first setup the mVertices correctly.
        mVertices = ByteBuffer
                .allocateDirect(mVerticesData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mVerticesData);
        mVertices.position(0);

        //setup shaders
        int vertexShader;
        int fragmentShader;
        int programObject;
        int[] linked = new int[1];

        // Load the vertex/fragment shaders
        vertexShader = myRenderer.LoadShader(GLES30.GL_VERTEX_SHADER, vShaderStr);
        fragmentShader = myRenderer.LoadShader(GLES30.GL_FRAGMENT_SHADER, fShaderStr);


        programObject = GLES30.glCreateProgram();

        if (programObject == 0) {
            Log.e(TAG, "So some kind of error, but what?");
            return;
        }

        GLES30.glAttachShader(programObject, vertexShader);
        GLES30.glAttachShader(programObject, fragmentShader);

        // Bind vPosition to attribute 0
        GLES30.glBindAttribLocation(programObject, 0, "vPosition");

        // Link the program
        GLES30.glLinkProgram(programObject);

        // Check the link status
        GLES30.glGetProgramiv(programObject, GLES30.GL_LINK_STATUS, linked, 0);

        if (linked[0] == 0) {
            Log.e(TAG, "Error linking program:");
            Log.e(TAG, GLES30.glGetProgramInfoLog(programObject));
            GLES30.glDeleteProgram(programObject);
            return;
        }

        // Store the program object
        mProgramObject = programObject;


    }


    //methode de dessin
    public void draw(float[] mvpMatrix) {

        // Use the program object
        GLES30.glUseProgram(mProgramObject);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgramObject, "uMVPMatrix");
        myRenderer.checkGlError("glGetUniformLocation");

        // get handle to fragment shader's vColor member
        mColorHandle = GLES30.glGetUniformLocation(mProgramObject, "vColor");


        // Apply projection and view transformation
        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        myRenderer.checkGlError("glUniformMatrix4fv");

        int VERTEX_POS_INDX = 0;
        mVertices.position(VERTEX_POS_INDX);  //just in case.  We did it already though.

        //add all the points to the space
        GLES30.glVertexAttribPointer(VERTEX_POS_INDX, 3, GLES30.GL_FLOAT,
                false, 0, mVertices);
        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDX);


        int startPos =0;
        int verticesPerface = 6;

        //draw front face
        GLES30.glUniform4fv(mColorHandle, 1, color1, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        //draw back face
        GLES30.glUniform4fv(mColorHandle, 1, color1, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, startPos, verticesPerface);
        startPos += verticesPerface;

        //draw left face
        GLES30.glUniform4fv(mColorHandle, 1, color2, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        //draw right face
        GLES30.glUniform4fv(mColorHandle, 1, color2, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        //draw top face
        GLES30.glUniform4fv(mColorHandle, 1, color3, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);
        startPos += verticesPerface;

        //draw bottom face
        GLES30.glUniform4fv(mColorHandle, 1, color3, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,startPos,verticesPerface);


    }
}