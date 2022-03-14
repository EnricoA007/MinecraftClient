package me.enrico.dynamic.minecraftclient.api.player.util;

public enum Dimension {

	OVERWORLD (0),
	NETHER (-1),
	END (1);
	
	private int id;
	
	private Dimension(int id) {
		this.id=id;
	}
	
	public int getID() {
		return id;
	}
	
	public static Dimension getDimensionFromID(int id) {
		for(Dimension g : values()) {
			if(g.getID() == id) {
				return g;
			}
		}
		throw new NullPointerException("Invalid dimension id " + id);
	}
	
}
