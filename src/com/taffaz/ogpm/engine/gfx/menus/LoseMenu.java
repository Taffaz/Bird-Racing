package com.taffaz.ogpm.engine.gfx.menus;

import com.taffaz.ogpm.engine.GameLauncher;
import com.taffaz.ogpm.engine.MainGame;
import com.taffaz.ogpm.engine.gfx.Screen;
import com.taffaz.ogpm.engine.gfx.sprites.ImageLoader;
import com.taffaz.ogpm.engine.listener.InputHandler;

public class LoseMenu extends Menu {
	
	public ImageLoader image = new ImageLoader("/menus/lose_menu.png");

	//public Sound ambientSound = Sound.AMBIENT;


	public LoseMenu(MainGame game, InputHandler input) {
		super(game, input);

		//ambientSound.loop();
	}

	@Override
	public void update() {
		if (input.enter) {
			GameLauncher.game.setMenu(new TitleMenu(GameLauncher.game, input));
		}
	}

	@Override
	public void render(Screen screen) {
		screen.fill(0xFF008080);
		screen.renderCenteredImage(image);

			}

}
