package com.taffaz.ogpm.engine.gfx.menus;

import java.awt.Color;

import javax.swing.JOptionPane;

import com.taffaz.ogpm.engine.GameLauncher;
import com.taffaz.ogpm.engine.MainGame;
import com.taffaz.ogpm.engine.gfx.Screen;
import com.taffaz.ogpm.engine.gfx.sprites.ImageLoader;
import com.taffaz.ogpm.engine.listener.InputHandler;

public class TitleMenu extends Menu {

	private int selected = 0;
	private String[] options = new String[0];
	public ImageLoader image = new ImageLoader("/menus/main_menu.png");

	//public Sound ambientSound = Sound.AMBIENT;

	public TitleMenu(MainGame game, InputHandler input) {
		super(game, input);
		if (MainGame.webstart) {
			options = new String[] { "Start Game", "How to play", "About", "Credits"};
		} else {
			options = new String[] { "Start Game", "How to play", "About", "Credits", "High Scores" };
		}
		selected = 0;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ambientSound.loop();
	}

	@Override
	public void update() {
		if (this.input.up) {
			selected--;
			this.input.clear();
		}
		if (input.down) {
			selected++;
			this.input.clear();
		}
		if (selected < 0) {
			selected += options.length;
		}
		if (selected >= options.length) {
			selected -= options.length;
		}
		if (input.enter) {
			this.input.clear();
			switch (selected) {
			case 0:
				//game.reset();
				MainGame.userName = JOptionPane.showInputDialog(GameLauncher.game, "Please Enter Name");
				if (MainGame.userName == null) MainGame.userName = "Anon";
				game.setMenu(null);
				//ambientSound.stop();
				break;
			case 1:
				game.setMenu(new InstructionMenu(this));
				break;
			case 2:
				game.setMenu(new AboutMenu(this));
				return;
			case 3:
				game.setMenu(new CreditsMenu(this));
				return;
			case 4:
				game.setMenu(new HighScoreMenu(this));
				return;
			}
		}
	}

	@Override
	public void render(Screen screen) {
		screen.fill(0xFF008080);
		screen.renderCenteredImage(image);

		for (int i = 0; i < options.length; i++) {
			String msg = options[i];
			if (i == selected) {
				msg = "> " + msg + " <";
			}
			screen.drawCenteredString(msg, MainGame.WIDTH * MainGame.SCALE / 2 + 200 - 1, 400 + (28 * i) - 2, 28, 1, Color.WHITE);
			screen.drawCenteredString(msg, MainGame.WIDTH * MainGame.SCALE / 2 + 200, 400 + (28 * i), 28, 1, new Color(0xFF000000));
		}

	}
}