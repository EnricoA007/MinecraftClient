package me.enrico.dynamic.minecraftclient.api.player.standard.event;

import me.enrico.dynamic.minecraftclient.api.player.standard.event.types.ChatMessageReceivedEvent;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.types.FinishedLoadingEvent;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.types.ServerListPingEvent;

public class AccessibleEvents {

	@SuppressWarnings("unchecked")
	public static Class<? extends Event>[] access() {
		return new Class[]
		{
			ChatMessageReceivedEvent.class,
			ServerListPingEvent.class,
			FinishedLoadingEvent.class
		};
	}

}
