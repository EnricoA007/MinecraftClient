package me.enrico.dynamic.minecraftclient.api.player.util;

public enum Difficulty {

	PEACEFUL (0),
	EASY (1),
	NORMAL (2),
	HARD (3);
	
	private int id;
	
	private Difficulty(int id) {
		this.id=id;
	}
	
	public int getID() {
		return id;
	}
	
	public static Difficulty getDifficultyFromID(int id) {
		for(Difficulty g : values()) {
			if(g.getID() == id) {
				return g;
			}
		}
		throw new NullPointerException("Invalid difficulty id " + id);
	}
	
}
