package me.enrico.dynamic.minecraftclient.api.packet.legacy;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;

public class LegacyPacketWrite extends Packet {
	
	private PacketDataWriter f;
	
	public LegacyPacketWrite(PacketDataWriter f) {
		this.f=f;
	}
	
	public PacketDataWriter getDataWriter() {
		return f;
	}
	
	@Override
	public void readPacketData() throws Exception {	}
	
	@Override
	public void writePacketData() throws Exception {
		this.getDataWriter().requestWrite(this.data);
	}
	
	public static interface PacketDataWriter {
		void requestWrite(PacketDataSerializer data);
	}

}
