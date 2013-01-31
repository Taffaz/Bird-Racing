package com.taffaz.ogpm.engine.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.taffaz.ogpm.engine.MainGame;
import com.taffaz.ogpm.engine.entities.Entity;
import com.taffaz.ogpm.engine.gfx.sprites.ImageLoader;
import com.taffaz.ogpm.engine.races.Race;

public class Screen {
	public int width, height;
	public int[] pixels;
	private Graphics graphics;
	private int xOffset, yOffset;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
	}

	public void setGraphics(Graphics g) {
		this.graphics = g;
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xffff00ff;
		}
	}

	public void renderEntity(int xOrig, int yOrig, Entity entity) {//, Race race) {
		for (int y = 0; y < entity.getSprite().height; y++) {
			int yp = yOrig + y;
			for (int x = 0; x < entity.getSprite().width; x++) {
				int xp = xOrig + x;
				int colour = entity.getSprite().pixels[x + y * entity.getSprite().width];
				if (colour != 0xFFFF00FF && colour != 0xFF7F007F) {
					pixels[xp + yp * width] = colour;
				}
			}
		}
	}

	private List<DrawnString> textToAdd = new ArrayList<DrawnString>();

	public void drawString(String msg, int x, int y, int size, int style, Color color) {
		synchronized (textToAdd) {
			textToAdd.add(new DrawnString(msg, x, y, size, style, color));
		}
	}

	private void setFont(int size, int style) {
		graphics.setFont(new Font("Helvetica", style, size));
	}

	public void drawCenteredString(String msg, int x, int y, int size, int style, Color color) {
		setFont(size, style);
		drawString(msg, x - getStringWidth(msg) / 2, y, size, style, color);
	}

	public void drawRightJustifiedString(String msg, int xOffset, int y, int size, int style, Color color) {
		setFont(size, style);
		drawString(msg, MainGame.WIDTH * MainGame.SCALE - getStringWidth(msg) - xOffset, y, size, style, color);
	}

	@SuppressWarnings("serial")
	public int getStringWidth(String msg) {
		if (graphics == null) {
			return 0;
		}
		FontMetrics metrics = graphics.getFontMetrics();
		if (metrics == null) {
			metrics = new FontMetrics(graphics.getFont()) {
			};
		}
		return metrics.stringWidth(msg);
	}

	public void renderText() {
		synchronized (textToAdd) {
			for (DrawnString s : textToAdd) {
				if (graphics == null) {
					return;
				}
				graphics.setColor(s.color);
				setFont(s.size, s.style);
				graphics.drawString(s.msg, s.x, s.y);
			}
		}
		textToAdd.clear();
	}

	public void renderHud(ImageLoader image, int[] bets, int birdSelected, double[] odds, int[] nums, int[] dens) {
		int xp = width / 2 - image.width / 2;
		int yp = height / 2 - image.height / 2;
		for (int y = 0; y < image.height; y++) {
			int yt = y + yp;
			for (int x = 0; x < image.width; x++) {
				int xt = x + xp;
				if (0 - image.width > xt || xt >= width || 0 - image.height > yt || yt >= height) {
					break;
				}
				if (xt < 0) {
					xt = 0;
				}

				if (yt < 0) {
					yt = 0;
				}
				int colour = image.pixels[x + y * image.width];
				
				if (colour != 0xFFFF00FF && colour != 0xFF7F007F) pixels[xt + yt * width] = colour;
			}
		}
		Color textColour = Color.YELLOW;
		for (int i = 0; i < 6; i++){
			if(birdSelected == (i + 1)){
				textColour = Color.RED;
			} else {
				textColour = Color.YELLOW;
			}
			drawString("Bird " + (i + 1), 10 , 30 + (i * 75),16,1,textColour);
			drawString("Current Bet = £" + bets[i], 10 , 50 + (i * 75),16,1,textColour);
			if(nums[i] != dens[i]){
				drawString("Odds = " + nums[i] + "/" + dens[i], 10 , 70 + (i * 75),16,1,textColour);
			} else {
				drawString("Odds = Evens", 10 , 70 + (i * 75),16,1,textColour);
			}
		}
		drawString(MainGame.userName, 216 * 3, 162*3, 16, 1, Color.YELLOW);
		drawString("Money: " + MainGame.cash, 216 * 3, 162*3 + 20, 16, 1, Color.YELLOW);
		
	}

	public void fill(int color) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

	public void renderCenteredImage(ImageLoader image) {
		int xp = width / 2 - image.width / 2;
		int yp = height / 2 - image.height / 2;
		for (int y = 0; y < image.height; y++) {
			int yt = y + yp;
			for (int x = 0; x < image.width; x++) {
				int xt = x + xp;
				if (0 - image.width > xt || xt >= width || 0 - image.height > yt || yt >= height) {
					break;
				}
				if (xt < 0) {
					xt = 0;
				}

				if (yt < 0) {
					yt = 0;
				}
				int colour = image.pixels[x + y * image.width];
				pixels[xt + yt * width] = colour;
			}
		}
	}

	/*public void renderButtons(ArrayList<Button> buttons) {

		for (Button but : buttons) {
			for (int y = 0; y < but.height; y++) {
				int yb = but.y + y;
				for (int x = 0; x < but.width; x++) {
					int xb = but.x + x;
					int colour = but.buttonImage.pixels[x + y * but.buttonImage.width];
					if (colour != 0xFFFF00FF && colour != 0xFF7F007F) pixels[xb + yb * width] = colour;
				}
			}
		}
	}*/

}