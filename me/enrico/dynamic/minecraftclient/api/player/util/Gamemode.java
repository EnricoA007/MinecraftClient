package me.enrico.dynamic.minecraftclient.api.player.util;

public enum Gamemode {

	SURVIVAL (0),
	CREATIVE (1),
	ADVENTURE (2),
	SPECTATOR (3),
	HARDCORE (0x08);
	
	private int id;
	
	private Gamemode(int id) {
		this.id=id;
	}
	
	public int getID() {
		return id;
	}
	
	public static Gamemode getGamemodeFromID(int id) {
		for(Gamemode g : values()) {
			if(g.getID() == id) {
				return g;
			}
		}
		throw new NullPointerException("Invalid gamemode id " + id);
	}
	
}
