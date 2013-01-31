package com.taffaz.ogpm.engine.entities;

import com.taffaz.ogpm.engine.gfx.Screen;
import com.taffaz.ogpm.engine.gfx.sprites.Sprite;

public abstract class Entity {

	public int x, y;
	public int size;
	private Sprite sprite;
	protected String name;

	public Entity(int size, String name, Sprite sprite) {
		this.size = size;
		this.name = name;
		this.sprite = sprite;
	}

	public abstract void render(Screen screen);
	
	public Sprite getSprite(){
		return sprite;
	}

	public abstract void update(double timeStep);
	
	public abstract String getName();
	
}