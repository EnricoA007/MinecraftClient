package me.enrico.dynamic.minecraftclient.api.packet.server.play;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;

public class SPacketKeepAlive extends Packet {

	private int id;
	
	public SPacketKeepAlive(PacketDataSerializer s) {
		super(s);
	}
	
	@Override
	public void readPacketData() {
		id=this.getPacketDataSerializer().readVarInt();
	}
	
	@Override
	public void writePacketData() {}
	
	public int getID() {
		return id;
	}
	
}
