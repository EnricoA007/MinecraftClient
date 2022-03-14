package me.enrico.dynamic.minecraftclient.api.player.standard.event.types;

import com.google.gson.Gson;
import me.enrico.dynamic.minecraftclient.api.MinecraftClient;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.Event;

public class ChatMessageReceivedEvent extends Event {

	private Gson gson;
	private String message;
	
	public ChatMessageReceivedEvent(MinecraftClient client, String message, Gson gson) {
		super(client);
		this.gson=gson;
		this.message=message;
	}

	public Gson getGson() {
		return gson;
	}

	public String getMessage() {
		return message;
	}

}
