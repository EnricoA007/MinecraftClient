package me.enrico.dynamic.minecraftclient.api.packet.client;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;

public class CPacketPlayer extends Packet{
	
	private boolean onGround;
	
	public CPacketPlayer(boolean onGround) {
		this.onGround=onGround;
	}
	
	@Override
	public void readPacketData() throws Exception {
		
	}
	
	@Override
	public void writePacketData() throws Exception {
		this.data.writeBoolean(onGround);
	}
	

}
