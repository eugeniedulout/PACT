package com.example.import1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES30;

//triangle modelisant la position du magasin
public class Triangle {

    private final String vertexShaderCode =

            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" + "  gl_Position = uMVPMatrix * vPosition;" + "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" + "  gl_FragColor = vColor;" + "}";

    private final FloatBuffer vertexBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // nb de coordonnee par sommet
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {
            // sens inverse des aiguilles d'une montre
            -0.35f,  -0.8f, 0.0f,   // top
            -0.4f, -0.9f, 0.0f,   // bottom right
            -0.3f, -0.9f, 0.0f    // bottom left
    };
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bits par vertex


    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 0.0f };

    public Triangle() {
        //initialisation du vertex byte buffer for pour les coordonnées
        ByteBuffer buffer = ByteBuffer.allocateDirect(
                triangleCoords.length * 4);
        buffer.order(ByteOrder.nativeOrder());

        //coordonnées floats
        vertexBuffer = buffer.asFloatBuffer();
        // ajout des coordonées
        vertexBuffer.put(triangleCoords);
        // on commence à la première coordonnée
        vertexBuffer.position(0);

        // le shader est initialisé
        int vertexShader = MyGLRenderer.loadShader(
                GLES30.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES30.glCreateProgram();             // Création d'un programme OpenGL vide
        GLES30.glAttachShader(mProgram, vertexShader);   // Ajout d'un vertexshader
        GLES30.glAttachShader(mProgram, fragmentShader); // Ajout d'un fragment shader
        GLES30.glLinkProgram(mProgram);

    }

    // draw()  permet de dessiner le triangle
    public void draw(float[] mvpMatrix) {
        // Ajout du programme à l'environnement OpenGL
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

        // setter de la couleur
        GLES30.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the triangle
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES30.glDisableVertexAttribArray(mPositionHandle);
    }

}
