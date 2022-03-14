package me.enrico.dynamic.minecraftclient.api.packet.server.login;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;

public class LoginSuccess extends Packet {

	private String uuid;
	private String username;
	
	public LoginSuccess(PacketDataSerializer s) {
		super(s);
	}
	
	@Override
	public void readPacketData() {
		this.uuid = getPacketDataSerializer().c(36);
		this.username = getPacketDataSerializer().c(16);
	}
	
	@Override
	public void writePacketData() {	}
	
	public String getUsername() {
		return username;
	}

	public String getUUID() {
		return uuid;
	}
	
}
