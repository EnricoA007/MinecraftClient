package me.enrico.dynamic.minecraftclient.api.player;

import java.util.UUID;

public class GameProfile {
	
	private String username;
	private UUID uuid;
	
	public GameProfile(String username, UUID uuid) {
		this.username=username;
		this.uuid=uuid;
	}

	public String getUsername() {
		return username;
	}
	
	public UUID getUUID() {
		return uuid;
	}

}
