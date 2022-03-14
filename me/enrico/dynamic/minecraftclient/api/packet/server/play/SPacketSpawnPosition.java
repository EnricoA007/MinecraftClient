package me.enrico.dynamic.minecraftclient.api.packet.server.play;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;
import me.enrico.dynamic.minecraftclient.api.player.util.Location;

public class SPacketSpawnPosition extends Packet {

	private Location spawnLocation;
	
	public SPacketSpawnPosition(PacketDataSerializer s) {
		super(s);
	}
	
	@Override
	public void readPacketData() throws Exception {
		this.spawnLocation=data.readLocation();
	}
	
	@Override
	public void writePacketData() throws Exception {}
	
	public Location getSpawnLocation() {
		return spawnLocation;
	}
	
}
