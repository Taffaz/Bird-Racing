package com.taffaz.ogpm.engine.entities;

import com.taffaz.ogpm.engine.gfx.Screen;
import com.taffaz.ogpm.engine.gfx.sprites.Sprite;

public class Racer extends Entity {
	
	private double position;
	private double speed;
	private double energy;
	private double lastSpeed;
	private double posChange;
	private double cumPosChange;
	private int startX;
	public double odds;
	public int numerator;
	public int denominator;
	public int number;

	private int topSpeed, accel, stamina;

	public Racer(int size, int x, int y, String name, Sprite sprite, int number, int num, int den) {
		super(size, name, sprite);
		this.x = x;
		this.y = y;
		this.startX = x;
		this.position = 0.0;
		this.speed = 0.0;
		this.energy = 100;

		this.accel = 3;
		this.topSpeed = 5;
		this.stamina = 10;
		
		generateOdds(num, den);
		
		this.posChange = 0;
		this.cumPosChange = 0;
		this.number = number;

	}
	
	private void generateOdds(int num, int den){
		numerator = num;
		denominator = den;
		
		if (numerator % denominator == 0) {
			numerator = numerator / denominator;
			denominator = 1;
		}
		
		odds = (double)numerator/(double)denominator;
	}

	@Override
	public void update(double timeStep) {
		updateSpeed();
		updatePosition(timeStep);
		cumPosChange += posChange;
		x = (int) (startX + cumPosChange);
	}

	@Override
	public void render(Screen screen) {
		screen.renderEntity(this.x, this.y, this);

	}

	private void updateSpeed() {
		lastSpeed = speed;
		speed = Math.min(topSpeed, lastSpeed + accel) * (energy / 100) * Math.random();
	}

	private void updatePosition(double timeStep) {
		double lastPosition = position;
		posChange = (((lastSpeed + speed) / 2) * timeStep) / 3;
		position = lastPosition + posChange;
		if (position > 300){
			position = 300;
			//x = 266;
		}

	}

	public void setAccel(int accel) {
		this.accel = accel;
	}

	public int getAccel() {
		return accel;
	}

	public void setTopSpeed(int topSpeed) {
		this.topSpeed = topSpeed;
	}

	public int getTopSpeed() {
		return topSpeed;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getStamina() {
		return stamina;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public double getEnergy() {
		return energy;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public double getPosition() {
		return position;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSpeed() {
		return speed;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public String toString(){
		return name + ": position / x = " + position + "/" + x;
	}

}
