package com.taffaz.ogpm.engine.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

  private boolean[] keys = new boolean[65536];
  public boolean up, down, left, right, enter, space, one, two, three, four, five, six, zero, plus, minus, esc, q;

  public void update() {
    up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
    down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
    left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
    right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
    enter = keys[KeyEvent.VK_ENTER];
    space = keys[KeyEvent.VK_SPACE];
    one = keys[KeyEvent.VK_1] || keys[KeyEvent.VK_NUMPAD1];
    two = keys[KeyEvent.VK_2] || keys[KeyEvent.VK_NUMPAD2];
    three = keys[KeyEvent.VK_3] || keys[KeyEvent.VK_NUMPAD3];
    four = keys[KeyEvent.VK_4] || keys[KeyEvent.VK_NUMPAD4];
    five = keys[KeyEvent.VK_5] || keys[KeyEvent.VK_NUMPAD5];
    six = keys[KeyEvent.VK_6] || keys[KeyEvent.VK_NUMPAD6];
    zero = keys[KeyEvent.VK_0] || keys[KeyEvent.VK_NUMPAD0];
    plus = keys[KeyEvent.VK_ADD] || (keys[KeyEvent.VK_SHIFT] && keys[KeyEvent.VK_EQUALS]);
    minus = keys[KeyEvent.VK_MINUS] || keys[KeyEvent.VK_SUBTRACT];
    q = keys[KeyEvent.VK_Q];
  }

  public void keyPressed(KeyEvent e) {
    keys[e.getKeyCode()] = true;
  }

  public void keyReleased(KeyEvent e) {
    keys[e.getKeyCode()] = false;
  }

  public void keyTyped(KeyEvent e) {
  }

  public void clear() {
    for (int i = 0; i < keys.length; i++) {
      keys[i] = false;
    }
  }
}