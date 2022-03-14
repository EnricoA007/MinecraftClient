package me.enrico.dynamic.minecraftclient.api.packet.handshake;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;

public class PacketLoginStart extends Packet {

	private String username;
	
	public PacketLoginStart(String username) {
		this.username=username;
	}
	
	@Override
	public void readPacketData() {
		
	}
	
	@Override
	public void writePacketData() {
		this.getPacketDataSerializer().writeString(username);
	}
	
}
