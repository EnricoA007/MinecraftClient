package me.enrico.dynamic.minecraftclient.api.packet.client;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;

public class CPacketMove extends Packet {
	
	private double x,y,z;
	private float yaw,pitch;
	private boolean onGround;
	
	public CPacketMove(double x, double y, double z, float yaw, float pitch, boolean onGround) {
		this.x=x;this.y=y;this.z=z;this.yaw=yaw;this.pitch=pitch;this.onGround=onGround;
	}

	@Override
	public void readPacketData() {}
	
	@Override
	public void writePacketData() {
		PacketDataSerializer d= getPacketDataSerializer();
		d.writeDouble(this.x);
		d.writeDouble(this.y);
		d.writeDouble(this.z);
		d.writeFloat(this.yaw);
		d.writeFloat(this.pitch);
		d.writeBoolean(this.onGround);
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
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public boolean isOnGround() {
		return onGround;
	}

}
