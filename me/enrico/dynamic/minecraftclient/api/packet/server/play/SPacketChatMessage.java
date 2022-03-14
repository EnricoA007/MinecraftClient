package me.enrico.dynamic.minecraftclient.api.packet.server.play;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;

public class SPacketChatMessage extends Packet {

	private String jsonMessage;
	/**
	 * 0 = Chat (Default Message)
	 * 1 = Chat (System Message)
	 * 2 = Actionbar
	 */
	private byte position;
	
	public SPacketChatMessage(PacketDataSerializer s) {
		super(s);
	}

	@Override
	public void readPacketData() {
		this.jsonMessage = getPacketDataSerializer().c(32767);
		this.position = getPacketDataSerializer().readByte();
	}

	@Override
	public void writePacketData() {	}

	public String getJsonMessage() {
		return jsonMessage;
	}
	
	public byte getPosition() {
		return position;
	}
	
}
