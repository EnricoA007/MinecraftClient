package me.enrico.dynamic.minecraftclient.api.player.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.enrico.dynamic.minecraftclient.api.MinecraftClient;

public class ServerPing {
	
	private Gson gson = MinecraftClient.gson;
	private String json, version,description;
	private int maxPlayers,onlinePlayers,protocol;
	
	public ServerPing(String json) {
		this.json=json;
		JsonObject base=gson.fromJson(json, JsonElement.class).getAsJsonObject();
		this.description = base.get("description").getAsString();
		JsonObject players = base.get("players").getAsJsonObject();
		this.maxPlayers = Integer.parseInt(players.get("max").getAsString());
		this.onlinePlayers = Integer.parseInt(players.get("online").getAsString());
		JsonObject version = base.get("version").getAsJsonObject();
		this.version = version.get("name").getAsString();
		this.protocol = Integer.parseInt(version.get("protocol").getAsString());
	}

	public String getVersion() {
		return version;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getOnlinePlayers() {
		return onlinePlayers;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public int getProtocol() {
		return protocol;
	}
	
	public String getAsJson() {
		return json;
	}
	
	@Override
	public String toString() {
		return json;
	}

}
