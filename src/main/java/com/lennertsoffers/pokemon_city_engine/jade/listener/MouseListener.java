package com.lennertsoffers.pokemon_city_engine.jade.listener;


import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private double xPos = 0.0;
    private double yPos = 0.0;
    private double xLast = 0.0;
    private double yLast = 0.0;
    private boolean dragging = false;

    private final Boolean[] mouseButtonPressed;

    private static MouseListener mouseListener;

    private MouseListener() {
        mouseButtonPressed = new Boolean[5];
        Arrays.fill(mouseButtonPressed, Boolean.FALSE);
    }

    public static MouseListener getInstance() {
        if (mouseListener == null) {
            mouseListener = new MouseListener();
        }

        return mouseListener;
    }

    public static void mousePositionCallback(long window, double xPos, double yPos) {
        getInstance().xLast = getInstance().xPos;
        getInstance().yLast = getInstance().yPos;
        getInstance().xPos = xPos;
        getInstance().yPos = yPos;
        getInstance().dragging = Arrays.stream(getInstance().mouseButtonPressed).anyMatch(Boolean::booleanValue);
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (button < getInstance().mouseButtonPressed.length) {
            if (action == GLFW_PRESS) {
                getInstance().mouseButtonPressed[button] = true;
            } else if (action == GLFW_RELEASE) {
                getInstance().mouseButtonPressed[button] = false;
                getInstance().dragging = false;
            }
        }
    }

    public static void endFrame() {
        getInstance().xLast = getInstance().xPos;
        getInstance().yLast = getInstance().yPos;
    }

    public static double getX() {
        return getInstance().xPos;
    }

    public static double getY() {
        return getInstance().yPos;
    }

    public static double getDx() {
        return getInstance().xLast - getInstance().xPos;
    }

    public static double getDy() {
        return getInstance().yLast - getInstance().yPos;
    }

    public static boolean isDragging() {
        return getInstance().dragging;
    }

    public static boolean isMouseButtonPressed(int button) {
        if (button < getInstance().mouseButtonPressed.length) {
            return getInstance().mouseButtonPressed[button];
        }

        return false;
    }
}
