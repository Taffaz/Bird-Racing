package com.taffaz.ogpm.engine.gfx.menus;

import com.taffaz.ogpm.engine.MainGame;
import com.taffaz.ogpm.engine.gfx.Screen;
import com.taffaz.ogpm.engine.listener.InputHandler;

public abstract class Menu {
  protected MainGame game;
  protected InputHandler input;

  public Menu(MainGame game, InputHandler input) {
    this.game = game;
    this.input = input;
  }

  public abstract void update();

  public abstract void render(Screen screen);
}