package com.example.import1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES30;

// Rectangle modelisant les rayons
public class Square {

    private final String vertexShaderCode =

            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" + "  gl_Position = uMVPMatrix * vPosition;" + "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" + "  gl_FragColor = vColor;" + "}";

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            0.3f,  0.9f, 0.0f,   // top right 1ere coordonnee horizontale  +  a gauche
            0.3f, 0.1f, 0.0f,   // bottom right
            0.4f, 0.1f, 0.0f,   // bottom left
            0.4f,  0.9f, 0.0f }; // top left

    private final short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    float color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };


    public Square() {
        // initialisation du vertex byte buffer for pour les coordonnées
        ByteBuffer buffer = ByteBuffer.allocateDirect(
                squareCoords.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);


        ByteBuffer dlb = ByteBuffer.allocateDirect(
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        // la shader
        int vertexShader = MyGLRenderer.loadShader(
                GLES30.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES30.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        mProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(mProgram, vertexShader);   // vertexshader
        GLES30.glAttachShader(mProgram, fragmentShader); // fragmentshader
        GLES30.glLinkProgram(mProgram);
    }


    // draw permet de dessiner le carre a partir de sa matrice mvpMatrix
    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment
        GLES30.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES30.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES30.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");

        // Setter de la couleur
        GLES30.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the square
        GLES30.glDrawElements(
                GLES30.GL_TRIANGLES, drawOrder.length,
                GLES30.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES30.glDisableVertexAttribArray(mPositionHandle);
    }

}