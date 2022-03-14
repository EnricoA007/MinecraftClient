package me.enrico.dynamic.minecraftclient.api.player.standard.event.types;

import me.enrico.dynamic.minecraftclient.api.MinecraftClient;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.Event;

public class FinishedLoadingEvent extends Event {
	
	public FinishedLoadingEvent(MinecraftClient client) {
		super(client);
	}

}
