package me.enrico.dynamic.minecraftclient.api.packet.server.status;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;

public class PacketServerListPingResponse extends Packet {

	private String json;
	
	public PacketServerListPingResponse(PacketDataSerializer s) {
		super(s);
	}
	
	@Override
	public void readPacketData() throws Exception {
		this.json = this.data.c(32767);
	}
	
	@Override
	public void writePacketData() throws Exception {	}
	
	public String getResponse() {
		return json;
	}

	
}
