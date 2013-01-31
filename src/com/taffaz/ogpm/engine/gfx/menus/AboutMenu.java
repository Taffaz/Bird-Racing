package com.taffaz.ogpm.engine.gfx.menus;

import java.awt.Color;

import com.taffaz.ogpm.engine.MainGame;
import com.taffaz.ogpm.engine.gfx.Screen;

public class AboutMenu extends Menu {

	  private TitleMenu parent;

	  private String[] msgs = new String[0];

	  public AboutMenu(TitleMenu parent) {
	    super(parent.game, parent.input);
	    this.parent = parent;
	    msgs = new String[] { "About", "Bird Racing was made by Taffaz ", "for the One Game Per Month Competition.", "(Press enter to return to main menu)" };
	  }

	  public void update() {
	    if (input.enter) {
	      game.setMenu(parent);
	      input.clear();
	      return;
	    }
	  }

	  public void render(Screen screen) {
	    screen.fill(0xFF008080);
	    screen.renderCenteredImage(parent.image);
	    for (int i = 0; i < msgs.length; i++) {
	      String msg = msgs[i];
	      int size = 20;
	      if (i == 0) {
	        size = 28;
	      }
	      if (i == msgs.length - 1) {
	        size = 12;
	      }
	      screen.drawCenteredString(msg, MainGame.WIDTH * MainGame.SCALE / 2 + 200, 400 + (28 * i) + 2, size, 1,
	          new Color(0xFF000000));
	    }
	  }
	}