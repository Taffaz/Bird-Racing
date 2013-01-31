package com.taffaz.ogpm.engine;

import java.applet.Applet;
import java.awt.BorderLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameLauncher extends Applet {
	
	public static MainGame game = new MainGame();
	
	public void init(){
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER);
		setPreferredSize(MainGame.DIMENSIONS);
		setMinimumSize(MainGame.DIMENSIONS);
		setMaximumSize(MainGame.DIMENSIONS);
	}
	
	public void start(){
		game.start();
	}
	public void stop(){
		game.stop();
	}

	public static void main(String[] args) {
		boolean isApplet = false;
		game.setPreferredSize(MainGame.DIMENSIONS);
		game.setMinimumSize(MainGame.DIMENSIONS);
		game.setMaximumSize(MainGame.DIMENSIONS);
		
		game.frame = new JFrame();
		game.frame.setResizable(false);
		game.frame.add(game);

		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		if (!isApplet) {
			game.frame.setTitle(MainGame.GAME_TITLE);
			game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		game.start();
	}

	
}
