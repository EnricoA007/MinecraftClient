package me.enrico.dynamic.minecraftclient.api.packet.server.play;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;
import me.enrico.dynamic.minecraftclient.api.player.util.Difficulty;

public class SPacketDifficultyChange extends Packet {

	private Difficulty difficulty;
	
	public SPacketDifficultyChange(PacketDataSerializer s) {
		super(s);
	}
	
	@Override
	public void readPacketData() throws Exception {
		this.difficulty=Difficulty.getDifficultyFromID(data.readUnsignedByte());
	}
	
	@Override
	public void writePacketData() throws Exception {}

	public Difficulty getDifficulty() {
		return difficulty;
	}
	
}
