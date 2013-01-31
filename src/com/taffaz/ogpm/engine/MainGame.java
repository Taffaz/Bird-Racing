package com.taffaz.ogpm.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.taffaz.ogpm.engine.gfx.Screen;
import com.taffaz.ogpm.engine.gfx.menus.LoadingMenu;
import com.taffaz.ogpm.engine.gfx.menus.Menu;
import com.taffaz.ogpm.engine.gfx.menus.TitleMenu;
import com.taffaz.ogpm.engine.highscores.HighscoreManager;
import com.taffaz.ogpm.engine.listener.InputHandler;
import com.taffaz.ogpm.engine.listener.MouseHandler;
import com.taffaz.ogpm.engine.races.Race;

public class MainGame extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6335122316862893216L;

	public static final String GAME_TITLE = "Bird Racer";
	public static final int WIDTH = 300;
	public static final int HEIGHT = 200;
	public static final int SCALE = 3;
	public static final Dimension DIMENSIONS = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);

	public JFrame frame;
	private Screen screen;

	public InputHandler input = new InputHandler();
	private MouseHandler mouseHandler = new MouseHandler();

	private Thread thread;
	private boolean isRunning = false;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public int fps = 0;
	public int ups = 0;

	public boolean isApplet = false;

	public Race race = null;
	private Menu menu;

	public static double cash;
	public static String userName;
	public static HighscoreManager hm;
	public static TitleMenu title;
	
	public static boolean webstart = false;

	//public static ArrayList<Button> buttons = new ArrayList<Button>();

	public MainGame() {
		this.isApplet = false;
		webstart = System.getProperty("javawebstart.version", null) != null;
		screen = new Screen(WIDTH, HEIGHT);
		
		addKeyListener(input);
		addMouseListener(mouseHandler);

		cash = 1000.0;
		
		hm = new HighscoreManager();

	}

	public synchronized void start() {
		if (this.isRunning) {
			return;
		}
		this.isRunning = true;
		this.thread = new Thread(this, "MainGameThread");
		this.thread.start();
	}

	public synchronized void stop() {
		if (!this.isRunning) {
			return;
		}
		this.isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long lastTimer = System.currentTimeMillis();
		double ns = 1000000000D / 60D;
		double delta = 0D;

		int fps = 0;
		int ups = 0;
		this.init();

		while (this.isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update(delta);
				delta--;
				ups++;
				//System.out.println("Update");
			}
			render();
			fps++;
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				this.fps = fps;
				this.ups = ups;
				fps = 0;
				ups = 0;
			}
		}

	}

	private void init() {
		race = new Race(10, 6, input, screen);
		title = new TitleMenu(this, input);
		setMenu(new LoadingMenu(this, input));
	}

	public void update(double delta) {
		input.update();
		if (menu != null) {
			menu.update();
		} else {
			race.update(delta);
		}
		
	}

	public void render() {
		BufferStrategy strategy = getBufferStrategy();
		if (strategy == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		Graphics g = strategy.getDrawGraphics();
		screen.setGraphics(g);

		if (menu != null) {
			menu.render(screen);
			// Rendering
		} else {

			screen.fill(0xff00ff00);
			race.render(screen);
		}
		//screen.renderButtons(buttons);
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		g.drawImage(this.image, 0, 0, getWidth(), getHeight(), null);
		screen.renderText();
		g.setColor(Color.WHITE);
		//g.drawString("FPS:UPS = " + fps + ":" + ups, 500, 50);

		g.dispose();
		strategy.show();
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/*	public void click(int mouseX, int mouseY){
			for(Button but : buttons){
				System.out.println("Mouse = (" + mouseX + ", " + mouseY + ") : Button = (" + but.bounds.x + ", " + but.bounds.y + ") and Width/Height = " + but.bounds.width + " / " + but.bounds.height);
				if(but.bounds.contains(new Point(mouseX, mouseY))){
					but.clicked();
				}
			}
		}*/

	
}
