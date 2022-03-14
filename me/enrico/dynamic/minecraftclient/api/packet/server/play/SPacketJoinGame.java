package me.enrico.dynamic.minecraftclient.api.packet.server.play;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;
import me.enrico.dynamic.minecraftclient.api.player.util.Difficulty;
import me.enrico.dynamic.minecraftclient.api.player.util.Dimension;
import me.enrico.dynamic.minecraftclient.api.player.util.Gamemode;

public class SPacketJoinGame extends Packet {
	
	private int entityID;
	private Gamemode gamemode;
	private Dimension dimension;
	private Difficulty difficulty;
	private int maxplayers;
	private String leveltype; // 16 chars
	private boolean reduced_debug_info,hardcore;
	
	public SPacketJoinGame(PacketDataSerializer s) {
		super(s);
	}
	
	@Override
	public void readPacketData() {
		PacketDataSerializer s = getPacketDataSerializer();
		this.entityID = s.readInt();
		int i = s.readUnsignedByte();
		this.hardcore = (i & 8) == 8;
		i = i & -9;
		this.gamemode = Gamemode.getGamemodeFromID(i);
		this.dimension = Dimension.getDimensionFromID((int)s.readByte());
		this.difficulty = Difficulty.getDifficultyFromID(s.readUnsignedByte());
		this.maxplayers = s.readUnsignedByte();
		this.leveltype = s.c(16);
		this.reduced_debug_info = s.readBoolean();
	}
	
	
	@Override
	public void writePacketData() {	}

	public int getEntityID() {
		return entityID;
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public Gamemode getGamemode() {
		return gamemode;
	}
	
	public String getLevelType() {
		return leveltype;
	}
	
	public int getMaxPlayers() {
		return maxplayers;
	}
	
	public boolean isReducedDebugInfo() {
		return reduced_debug_info;
	}

	public boolean isHardcore() {
		return hardcore;
	}
	
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	public void setGamemode(Gamemode gamemode) {
		this.gamemode = gamemode;
	}
	public void setHardcore(boolean hardcore) {
		this.hardcore = hardcore;
	}
	public void setLevelType(String leveltype) {
		this.leveltype = leveltype;
	}
	public void setMaxPlayers(int maxplayers) {
		this.maxplayers = maxplayers;
	}
	public void setReducedDebugInfo(boolean reduced_debug_info) {
		this.reduced_debug_info = reduced_debug_info;
	}
}
