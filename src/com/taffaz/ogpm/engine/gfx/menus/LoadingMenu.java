package com.taffaz.ogpm.engine.gfx.menus;

import com.taffaz.ogpm.engine.MainGame;
import com.taffaz.ogpm.engine.gfx.Screen;
import com.taffaz.ogpm.engine.gfx.sprites.ImageLoader;
import com.taffaz.ogpm.engine.listener.InputHandler;

public class LoadingMenu extends Menu {

	private long startTime;
	  private int loadDelay = 2;
	  private ImageLoader image = new ImageLoader("/menus/loading.png");
	  private Loader thread;

	  public LoadingMenu(MainGame game, InputHandler input) {
	    super(game, input);
	    startTime = System.currentTimeMillis();
	    load();
	  }

	  public void load() {
	    thread = new Loader();
	    thread.start();
	  }

	  @Override
	  public void update() {
	    if (thread.isDone && System.currentTimeMillis() - startTime >= loadDelay * 1000) {
	      game.setMenu(MainGame.title);
	    }
	  }

	  @Override
	  public void render(Screen screen) {
	    screen.fill(0xFF008080);
	    screen.renderCenteredImage(image);
	  }

	  class Loader extends Thread {
	    public boolean isDone = false;

	    public void run() {
	      isDone = true;
	    }
	  }
}
