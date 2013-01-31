package com.taffaz.ogpm.engine.races;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.taffaz.ogpm.engine.GameLauncher;
import com.taffaz.ogpm.engine.MainGame;
import com.taffaz.ogpm.engine.entities.Racer;
import com.taffaz.ogpm.engine.gfx.Screen;
import com.taffaz.ogpm.engine.gfx.menus.LoseMenu;
import com.taffaz.ogpm.engine.gfx.menus.TitleMenu;
import com.taffaz.ogpm.engine.gfx.sprites.ImageLoader;
import com.taffaz.ogpm.engine.gfx.sprites.Sprite;
import com.taffaz.ogpm.engine.listener.InputHandler;

public class Race {

	private static int[] possibleNums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	private static int[] possibleDens = { 1, 2, 3, 4, 1, 2, 3, 4 };

	private int length;
	private int numCompetitors;
	private InputHandler input;
	private ArrayList<Racer> racers;
	private boolean isRacing = false;
	private boolean raceFinished = false;
	private ImageLoader hud = new ImageLoader("/gui/race_gui.png");
	private int[] bets;
	private int[] nums;
	private int[] dens;
	private double[] odds;
	private int birdSelected = 0;
	private long lastKeyPress = System.currentTimeMillis();
	private Racer winningRacer = null;
	private boolean won = false;
	private String winningString = "";

	private Screen screen;

	public Race(int length, int numCompetitors, InputHandler input, Screen screen) {
		this.length = length;
		this.numCompetitors = numCompetitors;
		this.input = input;
		this.racers = new ArrayList<Racer>();
		this.screen = screen;
		init();
	}

	private void init() {
		bets = new int[numCompetitors];
		nums = new int[numCompetitors];
		dens = new int[numCompetitors];
		odds = new double[numCompetitors];
		won = false;

		Random rgen = new Random();

		for (int i = 0; i < possibleNums.length; i++) {
			int randomPosition = rgen.nextInt(possibleNums.length);
			int temp = possibleNums[i];
			possibleNums[i] = possibleNums[randomPosition];
			possibleNums[randomPosition] = temp;
		}

		for (int i = 0; i < possibleDens.length; i++) {
			int randomPosition = rgen.nextInt(possibleDens.length);
			int temp = possibleDens[i];
			possibleDens[i] = possibleDens[randomPosition];
			possibleDens[randomPosition] = temp;
		}

		for (int i = 0; i < numCompetitors; i++) {
			Racer tempRacer = new Racer(16, 65, 5 + (i * 25), "Bird " + (i + 1) + "", Sprite.BIRD, i, possibleNums[i], possibleDens[i]);

			racers.add(tempRacer);
			bets[i] = 0;
			odds[i] = tempRacer.odds;
			nums[i] = tempRacer.numerator;
			dens[i] = tempRacer.denominator;
		}
	}

	public void update(double delta) {
		long currTime = System.currentTimeMillis();
		if (input.up) {
			if ((currTime - lastKeyPress) > 150) {
				if (birdSelected <= 1) {
					birdSelected = 6;
				} else {
					birdSelected--;
				}
				lastKeyPress = System.currentTimeMillis();
			}
		}
		if (input.down) {
			if ((currTime - lastKeyPress) > 150) {
				if (birdSelected >= 6) {
					birdSelected = 1;
				} else {
					birdSelected++;
				}
				lastKeyPress = System.currentTimeMillis();
			}
		}

		if (input.enter && !raceFinished) isRacing = true;
		if (input.one && !isRacing) birdSelected = 1;
		if (input.two && !isRacing) birdSelected = 2;
		if (input.three && !isRacing) birdSelected = 3;
		if (input.four && !isRacing) birdSelected = 4;
		if (input.five && !isRacing) birdSelected = 5;
		if (input.six && !isRacing) birdSelected = 6;
		if (input.zero && !isRacing) birdSelected = 0;
		if ((input.plus || input.right) && !raceFinished && !isRacing && birdSelected > 0) {
			if ((currTime - lastKeyPress) > 150) {
				if (MainGame.cash >= 10) {
					bets[birdSelected - 1] += 10;
					MainGame.cash -= 10;
					lastKeyPress = System.currentTimeMillis();
				} else {
					bets[birdSelected - 1] += MainGame.cash;
					MainGame.cash -= MainGame.cash;
					lastKeyPress = System.currentTimeMillis();
				}
			}
		}
		if ((input.minus || input.left) && !raceFinished && !isRacing && birdSelected > 0 && bets[birdSelected - 1] > 0) {
			if ((currTime - lastKeyPress) > 150) {
				bets[birdSelected - 1] -= 10;
				MainGame.cash += 10;
				lastKeyPress = System.currentTimeMillis();
			}
		}
		if (input.space && !isRacing && raceFinished) {
			isRacing = false;
			if (MainGame.cash == 0) {
				racers.clear();
				raceFinished = false;
				init();
				MainGame.cash = 1000;

				GameLauncher.game.setMenu(new LoseMenu(GameLauncher.game, input));
			}
			newRace();
		}
		if (input.q && !isRacing) {
			isRacing = false;
			reset();
			MainGame.hm.addScore(MainGame.userName, (int) MainGame.cash);
			MainGame.cash = 1000;
		}
		if (isRacing) {
			if (!racers.isEmpty()) {
				for (Racer r : racers) {
					r.update(delta);
					if (r.getPosition() >= 215.0) {
						raceFinished = true;
					}
				}
				if (raceFinished) {
					finishRace();
				}
			}
		}
	}

	public void render(Screen screen) {
		screen.renderHud(hud, bets, birdSelected, odds, nums, dens);
		if (!racers.isEmpty()) {
			for (Racer r : racers) {
				r.render(screen);
			}
		}
		if (raceFinished) {
			screen.drawCenteredString("Winner", winningRacer.x * MainGame.SCALE + 8 * MainGame.SCALE, winningRacer.y * MainGame.SCALE + 4 * MainGame.SCALE, 16, Font.BOLD, Color.RED);
			if (won) {
				screen.drawString(winningString, 50, 480, 16, Font.BOLD, Color.YELLOW);

			}
			screen.drawString("Press q to quit.", 50, 560, 16, Font.BOLD, Color.YELLOW);
			screen.drawString("Press Spacebar to start new race.", 50, 580, 16, Font.BOLD, Color.YELLOW);
		} else if (!raceFinished && !isRacing){
			screen.drawString("Use Up and Down arrow to select bird.", 50, 480, 16, Font.BOLD, Color.YELLOW);
			screen.drawString("Use Right and Left arrow to change bet amount.", 50, 500, 16, Font.BOLD, Color.YELLOW);
			screen.drawString("Press Enter to start race.", 50, 520, 16, Font.BOLD, Color.YELLOW);
			screen.drawString("Press q to quit.", 50, 540, 16, Font.BOLD, Color.YELLOW);
		}
		
	}

	public void finishRace() {
		isRacing = false;
		Collections.sort(racers, new Comparator<Racer>() {
			public int compare(Racer a, Racer b) {
				if (a.getPosition() < b.getPosition()) {
					return 1;
				} else if (a.getPosition() > b.getPosition()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		winningRacer = racers.get(0);

//		System.out.println("bet = " + bets[winningRacer.number] + " | odds = " + odds[winningRacer.number] + "| winnings = " + ((bets[winningRacer.number] * odds[winningRacer.number]) + bets[winningRacer.number]));

		double winnings = ((bets[winningRacer.number] * odds[winningRacer.number]) + bets[winningRacer.number]);

		double temp = winnings * 100;
		temp = Math.round(temp);
		temp = temp / 100;
		winnings = temp;
		
		winningString = "You Won £" + winnings + "!";
		
		if (winnings > 0) 
			won = true;
		else
			won = false;
		
		MainGame.cash += winnings;

		temp = MainGame.cash * 100;
		temp = Math.round(temp);
		temp = temp / 100;
		MainGame.cash = temp;
		
		

	}

	public void newRace() {
		racers.clear();
		raceFinished = false;
		init();
	}

	public void reset() {
		racers.clear();
		raceFinished = false;
		init();
		GameLauncher.game.setMenu(new TitleMenu(GameLauncher.game, input));
	}
}
