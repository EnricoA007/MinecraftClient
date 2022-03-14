package me.enrico.dynamic.minecraftclient.api.player.util;

public class Location extends ILocation {

	private double x,y,z;
	private float yaw,pitch;
	
	public Location(double x, double y, double z) {
		super(x,y,z);
	}
	
	public Location(double x, double y, double z, float yaw, float pitch) {
		super(x,y,z,yaw,pitch);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public float getYaw() {
		if(yaw == -1) throw new NullPointerException();
		return yaw;
	}
	
	public float getPitch() {
		if(pitch == -1) throw new NullPointerException();
		return pitch;
	}
	
}
