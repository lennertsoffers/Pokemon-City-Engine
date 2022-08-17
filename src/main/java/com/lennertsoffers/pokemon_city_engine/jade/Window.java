package com.lennertsoffers.pokemon_city_engine.jade;

import com.lennertsoffers.pokemon_city_engine.jade.listener.KeyListener;
import com.lennertsoffers.pokemon_city_engine.jade.listener.MouseListener;
import com.lennertsoffers.pokemon_city_engine.jade.scene.*;
import com.lennertsoffers.pokemon_city_engine.util.Time;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private long glfwWindow;

    private static Window window;
    private static Scene currentScene;

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final String TITLE = "Pokemon City";

    private Window() {}

    public static Window getInstance() {
        if (window == null) {
            window = new Window();
        }

        return window;
    }

    public void run() {
        System.out.println("Startup Pokemon City");

        this.init();
        this.loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        GLFWErrorCallback glfwErrorCallback = glfwSetErrorCallback(null);
        if (glfwErrorCallback != null) {
            glfwErrorCallback.free();
        }
    }

    public void changeScene(SceneType newScene) {
        switch (newScene) {
            case CITY -> currentScene = new CityScene();
            case MENU -> currentScene = new MenuScene();
            default -> currentScene = new LoadScene();
        }
    }

    private void init() {
        // Setup error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Check if GLFW is initialized
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        // Window hidden after creation
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        // Make the window resizable
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        // Maximize the window
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        this.glfwWindow = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);
        if (this.glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        // Register input listeners
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePositionCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyPressedCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Change scene
        changeScene(SceneType.LOAD);

        // Make window visible
        GL.createCapabilities();
    }

    private void loop() {
        // Initialize variables for calculation of the duration of a game loop iteration
        float beginTime = Time.getTimeRunning();
        float endTime;
        float dt = -1.0f;

        // Game Loop
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            // Show a static color
            glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            // Call eventListeners
            if (KeyListener.isKeyPressed(GLFW_KEY_K)) {
                System.out.println("k");
            }

            currentScene.update(dt);

            glfwSwapBuffers(glfwWindow);

            // Calculate dt
            // dt = time for one game loop
            endTime = Time.getTimeRunning();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }

}
