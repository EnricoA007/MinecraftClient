package me.enrico.dynamic.minecraftclient.api.player;

import me.enrico.dynamic.minecraftclient.api.MinecraftClient;
import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.handlers.event.IPacketReceiver;
import me.enrico.dynamic.minecraftclient.api.player.standard.DefaultPlayer;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.types.FinishedLoadingEvent;

public abstract class Player {
	
	private boolean finished = false;
	private GameProfile profile;
	private MinecraftClient client;
	
	public Player(GameProfile profile, MinecraftClient client) {
		this.profile=profile;this.client=client;
	}
	
	public MinecraftClient getClient() {
		return client;
	}
	
	public GameProfile getProfile() {
		return profile;
	}
	
	public void setProfile(GameProfile profile) {
		this.profile=profile;
	}
	
	public void sendPacket(Packet packet) {
		this.client.sendPacket(packet);
	}

	public void callFinish() throws IllegalAccessException {
		if(this.finished) {
			throw new IllegalAccessException("Already finished");
		}
		this.finished =true;
		if(this.getClient().getPlayer() instanceof DefaultPlayer) {
			DefaultPlayer p = (DefaultPlayer) this;
			p.eventFactory.callEvent(new FinishedLoadingEvent(client));
		}
	}
	
	public boolean isFinished() {
		return this.finished;
	}
	
	public abstract IPacketReceiver getReceiver();

    public abstract void disconnect();
}
