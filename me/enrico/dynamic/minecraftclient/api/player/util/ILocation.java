package me.enrico.dynamic.minecraftclient.api.player.util;

public abstract class ILocation {
	
	private double x,y,z;
	private float yaw,pitch;
	
	public ILocation(double x, double y, double z) {
		this(x,y,z,-1,-1);
	}
	
	public ILocation(double x, double y, double z, float yaw, float pitch) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.yaw=yaw;
		this.pitch=pitch;
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
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}

}
