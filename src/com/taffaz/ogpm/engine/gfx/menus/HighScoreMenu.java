package com.taffaz.ogpm.engine.gfx.menus;

import java.awt.Color;
import java.util.ArrayList;

import com.taffaz.ogpm.engine.MainGame;
import com.taffaz.ogpm.engine.gfx.Screen;
import com.taffaz.ogpm.engine.highscores.Score;

public class HighScoreMenu extends Menu {

	  private TitleMenu parent;
	  ArrayList<Score> scores = new ArrayList<Score>();

	  private String[] msgs = new String[0];

	  public HighScoreMenu(TitleMenu parent) {
	    super(parent.game, parent.input);
	    this.parent = parent;
	    scores = MainGame.hm.getHighscores();
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
	    screen.drawCenteredString("High Scores", MainGame.WIDTH * MainGame.SCALE / 2 + 200, 360, 28, 1,
		          new Color(0xFF000000));
	    for (int i = 0; i < scores.size(); i++) {
	      int size = 18;
	      Score s = scores.get(i);
	      screen.drawString((i+1)+":", MainGame.WIDTH * MainGame.SCALE / 2 + 120, 380 + (28 * i) + 5, size, 1,
		          new Color(0xFF000000));
	      screen.drawString(s.getName(), MainGame.WIDTH * MainGame.SCALE / 2 + 150, 380 + (28 * i) + 5, size, 1,
	          new Color(0xFF000000));
	      screen.drawRightJustifiedString(s.getScore()+"", MainGame.WIDTH * MainGame.SCALE - 730, 380 + (28 * i) + 5, size, 1,
		          new Color(0xFF000000));
	    }
	    screen.drawCenteredString("(Press enter to return to main menu)", MainGame.WIDTH * MainGame.SCALE / 2 + 200, 510 + 5, 12, 1,
		          new Color(0xFF000000));
	  }
	}