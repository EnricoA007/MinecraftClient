package me.enrico.dynamic.minecraftclient.api.player.standard.event.types;

import me.enrico.dynamic.minecraftclient.api.MinecraftClient;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.Event;
import me.enrico.dynamic.minecraftclient.api.player.util.ServerPing;

public class ServerListPingEvent extends Event {

	private ServerPing ping;
	
	public ServerListPingEvent(MinecraftClient client, ServerPing ping) {
		super(client);
		this.ping=ping;
	}
	
	public ServerPing getPing() {
		return ping;
	}

	public String getPingJson() {
		return ping.toString();
	}
	
}
