package com.taffaz.ogpm.engine.gfx.sprites;

public class Sprite {

	  private SpriteSheet[] spriteSheets = {new SpriteSheet("/sprite_sheets/mobs.png"), new SpriteSheet("/sprite_sheets/spritesheet.png") };
	  
	  public static Sprite BIRD = new Sprite("Bird", 0, 0, 16, 16, "MOB");
	  
	  public static Sprite VOID = new Sprite(0x000000);
	  
	  public String name;
	  public int x, y;
	  public int width, height;
	  public int[] pixels;

	  public Sprite(String name, int x, int y, int width, int height, String spriteType) {
	    this.name = name;
	    this.x = x << 4;
	    this.y = y << 4;
	    this.width = width;
	    this.height = height;
	    this.pixels = new int[width * height];
	    this.create(spriteType);
	  }

	  public Sprite(int colour) {
	    this.x = 0;
	    this.y = 0;
	    this.width = 16;
	    this.height = 16;
	    this.pixels = new int[width * height];
	    create(colour);
	  }

	  private void create(String spriteType) {
	    for (int y = 0; y < height; y++) {
	      for (int x = 0; x < width; x++) {
	        SpriteSheet sheet = getSpriteSheet(spriteType);
	        pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.width];
	      }
	    }
	  }

	  private void create(int colour) {
	    for (int y = 0; y < height; y++) {
	      for (int x = 0; x < width; x++) {
	        pixels[x + y * width] = colour;
	      }
	    }
	  }

	  private SpriteSheet getSpriteSheet(String type) {
	    if (type.equals("MOB")) {
	      return spriteSheets[0];
	    } else {
	      return spriteSheets[2];
	    }
	  }

	}