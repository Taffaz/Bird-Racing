package com.taffaz.ogpm.engine.gfx.gui;

import java.awt.Rectangle;

import com.taffaz.ogpm.engine.gfx.sprites.ImageLoader;

public class Button {

	public int x, y, width, height;
	public Rectangle bounds;
	public String imgPath;
	public ImageLoader buttonImage;
	
	public Button(int x, int y, String imgPath){
		this.x = x;
		this.y = y;
		buttonImage = new ImageLoader(imgPath);
		this.width = buttonImage.width;
		this.height = buttonImage.height;
		this.bounds = new Rectangle(this.x, this.y, this.width, this.height);
		
	}
	
	public void clicked(){
		System.out.println("Clicked");
	}
	
}
