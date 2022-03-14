package me.enrico.dynamic.minecraftclient.api.packet.handshake;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;

public class PacketHandshake extends Packet {

	private int protocol,port;
	private String address;
	private ConnectionState state;
	
	public PacketHandshake(int protocol, String address, int port, ConnectionState state){
		super();
		this.protocol=protocol;
		this.port=port;
		this.address=address;
		this.state=state;
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
	
	public int getProtocol() {
		return protocol;
	}
	
	public ConnectionState getState() {
		return state;
	}
	
	@Override
	public void readPacketData() {
		
	}
	
	@Override
	public void writePacketData() {
		this.getPacketDataSerializer().writeVarInt(protocol);
		this.getPacketDataSerializer().writeString(this.address);
		this.getPacketDataSerializer().writeShort(port);
		this.getPacketDataSerializer().writeVarInt(state.getState());
	}
	
	public enum ConnectionState {
		
		STATUS (1),
		LOGIN (2);
		
		private int i;
		
		private ConnectionState(int i) {
			this.i=i;
		}
		
		public int getState() {
			return i;
		}
		
	}
	
	
}
