package me.enrico.dynamic.minecraftclient.api.player.standard;

import me.enrico.dynamic.minecraftclient.api.MinecraftClient;
import me.enrico.dynamic.minecraftclient.api.MinecraftClient.AsyncCall;
import me.enrico.dynamic.minecraftclient.api.packet.handlers.event.IPacketReceiver;
import me.enrico.dynamic.minecraftclient.api.packet.handshake.PacketHandshake;
import me.enrico.dynamic.minecraftclient.api.packet.handshake.PacketHandshake.ConnectionState;
import me.enrico.dynamic.minecraftclient.api.packet.legacy.LegacyPacketWrite;
import me.enrico.dynamic.minecraftclient.api.packet.server.status.PacketServerListPingResponse;
import me.enrico.dynamic.minecraftclient.api.player.GameProfile;
import me.enrico.dynamic.minecraftclient.api.player.Player;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.AccessibleEvents;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.EventFactory;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.types.ServerListPingEvent;
import me.enrico.dynamic.minecraftclient.api.player.util.ServerPing;

public class HandshakePlayer extends Player {

	public EventFactory eventFactory = new EventFactory(AccessibleEvents.access());
	
	public HandshakePlayer(GameProfile profile, MinecraftClient client) {
		super(profile, client);
	}

	@Override
	public IPacketReceiver getReceiver() {
		return packet -> {
			System.out.println(packet.getClass().getSimpleName());
			if(packet instanceof PacketServerListPingResponse) {
				eventFactory.callEvent(new ServerListPingEvent(this.getClient(), new ServerPing(((PacketServerListPingResponse)packet).getResponse())));
			}
		};
	}
	
	@Override
	public void callFinish() throws IllegalAccessException {
		MinecraftClient client = getClient();
		sendPacket(new PacketHandshake(client.getProtocol(), client.getAddress(), client.getPort(), ConnectionState.STATUS));
		sendPacket(new LegacyPacketWrite(data -> {data.writeVarInt(0x00);}));
		
		new AsyncCall(() -> {
			try {
				super.callFinish();
			} catch (IllegalAccessException e) {}
		}, 50).startTimer();
	}

	@Override
	public void disconnect() {
		this.getClient().getChannel().close().syncUninterruptibly();
	}
	
}