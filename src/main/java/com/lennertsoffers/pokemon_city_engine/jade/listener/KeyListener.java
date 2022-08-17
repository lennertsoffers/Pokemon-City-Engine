package com.lennertsoffers.pokemon_city_engine.jade.listener;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
    private final Boolean[] keyPressed;

    private static KeyListener keyListener;

    private KeyListener() {
        keyPressed = new Boolean[350];
        Arrays.fill(keyPressed, Boolean.FALSE);
    }

    public static KeyListener getInstance() {
        if (keyListener == null) {
            keyListener = new KeyListener();
        }

        return keyListener;
    }

    public static void keyPressedCallback(long window, int key, int scanCode, int action, int mods) {
        if (key < getInstance().keyPressed.length) {
            if (action == GLFW_PRESS) {
                getInstance().keyPressed[key] = true;
            } else if (action == GLFW_RELEASE) {
                getInstance().keyPressed[key] = false;
            }
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        if (keyCode < getInstance().keyPressed.length) {
            return getInstance().keyPressed[keyCode];
        }

        return false;
    }
}
