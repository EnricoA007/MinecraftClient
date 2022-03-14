package me.enrico.dynamic.minecraftclient.api.packet.client;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;

public class CPacketUpdatePosition extends Packet {
	
	private double x,y,z;
	private boolean onGround;
	
	public CPacketUpdatePosition(double x, double y, double z, boolean onGround) {
		this.x=x;this.y=y;this.z=z;this.onGround=onGround;
	}
	
	@Override
	public void readPacketData() {}
	
	@Override
	public void writePacketData() {
		PacketDataSerializer d= getPacketDataSerializer();
		d.writeDouble(this.x);
		d.writeDouble(this.y);
		d.writeDouble(this.z);
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
	
	public boolean isOnGround() {
		return onGround;
	}
	
}
