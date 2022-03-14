package me.enrico.dynamic.minecraftclient.api.packet;

import io.netty.buffer.Unpooled;

public abstract class Packet {

	public PacketDataSerializer data;
	
	public Packet(PacketDataSerializer data) {
		this.data=data;
	}
	
	public Packet() {
		this.data=new PacketDataSerializer(Unpooled.buffer());
	}
	
	public PacketDataSerializer getPacketDataSerializer() {
		return data;
	}
	
	public abstract void readPacketData() throws Exception;
	public abstract void writePacketData() throws Exception;
	
}
