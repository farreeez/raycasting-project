import org.lwjgl.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GraphicsRendering {
    private static long window;

    public static void startRendering(){
        initialiseWindow();
        gameLoop();

        glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

        glfwTerminate();
		glfwSetErrorCallback(null).free();
    }

    private static void gameLoop() {
    }

    private static void initialiseWindow() {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }
    }
}
