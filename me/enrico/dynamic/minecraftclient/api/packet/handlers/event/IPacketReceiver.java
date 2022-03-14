package me.enrico.dynamic.minecraftclient.api.packet.handlers.event;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;

public interface IPacketReceiver {

	void receive(Packet packet);
	
}
