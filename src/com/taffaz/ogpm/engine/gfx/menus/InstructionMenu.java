package com.taffaz.ogpm.engine.gfx.menus;

import java.awt.Color;

import com.taffaz.ogpm.engine.MainGame;
import com.taffaz.ogpm.engine.gfx.Screen;

public class InstructionMenu extends Menu {

	  private TitleMenu parent;

	  private String[] msgs = new String[0];

	  public InstructionMenu(TitleMenu parent) {
	    super(parent.game, parent.input);
	    this.parent = parent;
	    msgs = new String[] { 
	    		"How To Play:", 
	    		"Use 1-6 or the Up and Down arrow to select a bird", 
	    		"+ and - will change the bet amount by £10",
	    		"Right and Left arrow also change bet amount.",
	    		"Enter will start the race", 
	    		"Space will reset the race once the race is over", 
	    		"The \"q\" key will return to the main menu",
	    		"(Press enter to return to main menu)" };
	  }

	  public void update() {
	    if (input.enter) {
	      game.setMenu(parent);
	      input.clear();
	      return;
	    }
	  }

	  public void render(Screen screen) {
	    //screen.fill(0xFF008080);
	    screen.renderCenteredImage(parent.image);
	    for (int i = 0; i < msgs.length; i++) {
	      String msg = msgs[i];
	      int size = 18;
	      if (i == 0) {
	        size = 28;
	      }
	      if (i == msgs.length - 1) {
	        size = 12;
	      }
	      screen.drawString(msg, MainGame.WIDTH * MainGame.SCALE / 2 - 100, 380 + (28 * i) + 2, size, 1,
	          new Color(0xFF000000));
	    }
	  }
	}