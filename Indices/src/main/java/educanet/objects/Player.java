package educanet.objects;

import educanet.Shaders;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Player {

    private static final float[] vertices = {
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
    };

    private static final float[] colors = {
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
    };

    private static final int[] indices = {
            0, 1, 3,
            1, 2, 3
    };

    private static int squareVaoId;
    private static int squareVboId;
    private static int squareEboId;
    private static int colorsId;

    private static int uniformColorLocation;
    private static int uniformMatrixLocation;

    private static Matrix4f matrix = new Matrix4f()
            .identity()
            .scale(0.25f, 0.25f, 0.25f);
    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public Player() {
        uniformColorLocation = GL33.glGetUniformLocation(Shaders.shaderProgramId, "outColor");
        uniformMatrixLocation = GL33.glGetUniformLocation(Shaders.shaderProgramId, "matrix");

        squareVaoId = GL33.glGenVertexArrays();
        squareVboId = GL33.glGenBuffers();
        squareEboId = GL33.glGenBuffers();
        colorsId = GL33.glGenBuffers();

        GL33.glBindVertexArray(squareVaoId);

        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, squareEboId);
        IntBuffer ib = BufferUtils.createIntBuffer(indices.length)
                .put(indices)
                .flip();
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareVboId);

        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                .put(vertices)
                .flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);

        MemoryUtil.memFree(fb);

        GL33.glUseProgram(Shaders.shaderProgramId);
        GL33.glUniform3f(uniformColorLocation, 1.0f, 1.0f, 1.0f);

        matrix.get(matrixBuffer);

        GL33.glUniformMatrix4fv(uniformMatrixLocation, false, matrixBuffer);

        MemoryUtil.memFree(fb);
    }

    public static float[] getVertices() {
        return vertices;
    }

    public static float[] getColors() {
        return colors;
    }

    public static int[] getIndices() {
        return indices;
    }

    public static int getSquareVaoId() {
        return squareVaoId;
    }

    public static int getSquareVboId() {
        return squareVboId;
    }

    public static int getSquareEboId() {
        return squareEboId;
    }

    public static int getColorsId() {
        return colorsId;
    }

    public static Matrix4f getMatrix() {
        return matrix;
    }

    public static int getUniformColorLocation() {
        return uniformColorLocation;
    }

    public static int getUniformMatrixLocation() {
        return uniformMatrixLocation;
    }

    public static FloatBuffer getMatrixBuffer() {
        return matrixBuffer;
    }
}
