package com.taffaz.ogpm.engine.gfx.menus;

import java.awt.Color;

import com.taffaz.ogpm.engine.MainGame;
import com.taffaz.ogpm.engine.gfx.Screen;

public class CreditsMenu extends Menu {

	  private TitleMenu parent;

	  private String[] msgs = new String[0];

	  public CreditsMenu(TitleMenu parent) {
	    super(parent.game, parent.input);
	    this.parent = parent;
	    msgs = new String[] { "Credits", "Thanks go to the following people:","Stephanie Spencer-Grant for the motivation to finish these games","Ryan van Zeben (Youtube: Designs by Zephyr) for his awesome Youtube series", "(Press enter to return to main menu)" };
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
	      int size = 16;
	      if (i == 0) {
	        size = 28;
	      } else if (i==1){
	    	  size = 16;
	      }
	      if (i == msgs.length - 1) {
	        size = 12;
	      }
	      screen.drawString(msg, MainGame.WIDTH * MainGame.SCALE / 2 - 400, 400 + (28 * i) + 2, size, 1,
	          new Color(0xFF000000));
	    }
	  }
	}