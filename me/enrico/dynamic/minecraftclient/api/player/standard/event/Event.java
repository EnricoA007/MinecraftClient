package me.enrico.dynamic.minecraftclient.api.player.standard.event;

import me.enrico.dynamic.minecraftclient.api.MinecraftClient;

public abstract class Event {
	
	private MinecraftClient client;
	
	public Event(MinecraftClient client) {
		this.client=client;
	}
	
	public MinecraftClient getClient() {
		return client;
	}

}
