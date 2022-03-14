package me.enrico.dynamic.minecraftclient.api.packet.client;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;

public class CPacketKeepAlive extends Packet {

	private int id;
	
	public CPacketKeepAlive(int id) {
		this.id=id;
	}
	
	@Override
	public void readPacketData() {}
	
	@Override
	public void writePacketData() {
		getPacketDataSerializer().writeVarInt(this.id);
	}
	
	
	public int getID() {
		return id;
	}
	
}
