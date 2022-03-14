package me.enrico.dynamic.minecraftclient.api.packet.server.login;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;

public class SetCompressionPacket extends Packet {

	private int threshold;
	
	public SetCompressionPacket(PacketDataSerializer s) {
		super(s);
	}
	
	@Override
	public void readPacketData() {
		this.threshold = getPacketDataSerializer().readVarInt();
	}

	@Override
	public void writePacketData() {}

	public int getThreshold() {
		return threshold;
	}
	
}
