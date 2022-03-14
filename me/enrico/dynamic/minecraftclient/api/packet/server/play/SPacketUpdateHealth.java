package me.enrico.dynamic.minecraftclient.api.packet.server.play;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;

public class SPacketUpdateHealth extends Packet {
	
	private float health;
	private int food;
	private int saturation;
	
	public SPacketUpdateHealth(PacketDataSerializer s) {
		super(s);
	}
	
	@Override
	public void readPacketData() throws Exception {
		this.health = data.readFloat();
		this.food = data.readVarInt();
		this.saturation = data.readVarInt();
	}
	
	@Override
	public void writePacketData() throws Exception {}

	public int getFood() {
		return food;
	}
	
	public float getHealth() {
		return health;
	}
	
	public int getSaturation() {
		return saturation;
	}
	
}
