package me.enrico.dynamic.minecraftclient.api.packet.client;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;

public class CPacketSendChat extends Packet {
	
	private String message;
	
	public CPacketSendChat(String message) {
		this.message=message;
	}
	
	@Override
	public void readPacketData() { }
	
	@Override
	public void writePacketData() {
		this.getPacketDataSerializer().writeString(message);
	}
	

}
