package me.enrico.dynamic.minecraftclient.api.packet;

import me.enrico.dynamic.minecraftclient.api.packet.client.CPacketKeepAlive;
import me.enrico.dynamic.minecraftclient.api.packet.client.CPacketMove;
import me.enrico.dynamic.minecraftclient.api.packet.client.CPacketPlayer;
import me.enrico.dynamic.minecraftclient.api.packet.client.CPacketSendChat;
import me.enrico.dynamic.minecraftclient.api.packet.client.CPacketUpdatePosition;
import me.enrico.dynamic.minecraftclient.api.packet.handshake.PacketHandshake;
import me.enrico.dynamic.minecraftclient.api.packet.handshake.PacketLoginStart;
import me.enrico.dynamic.minecraftclient.api.packet.server.login.LoginSuccess;
import me.enrico.dynamic.minecraftclient.api.packet.server.login.SetCompressionPacket;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketChatMessage;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketDifficultyChange;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketJoinGame;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketKeepAlive;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketPluginMessage;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketPositionAndLook;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketSpawnPosition;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketUpdateHealth;
import me.enrico.dynamic.minecraftclient.api.packet.server.status.PacketServerListPingResponse;

public class PacketFactory {

	public static Packet getPacketFromID(int packetID, PacketDataSerializer s, boolean isPlaying) {
		
		if(isPlaying)
		{
			
			if(packetID == 0x21) {
				return new SPacketKeepAlive(s);
			}else if(packetID == 0x01) {
				return new SPacketJoinGame(s);
			}else if(packetID == 0x00) {
				return new SPacketKeepAlive(s);
			}else if(packetID == 0x02) {
				return new SPacketChatMessage(s);
			}else if(packetID == 0x3F) {
				return new SPacketPluginMessage(s);
			}else if(packetID == 0x41) {
				return new SPacketDifficultyChange(s);
			}else if(packetID == 0x05) {
				return new SPacketSpawnPosition(s);
			}else if(packetID == 0x08) {
				return new SPacketPositionAndLook(s);
			}else if(packetID == 0x06) {
				return new SPacketUpdateHealth(s);
			}else {
//				System.out.println("Unhandled packet 0x" + Integer.toHexString(packetID));
			}
			
		}
		else
		{

			if(packetID == 0x02) {
				return new LoginSuccess(s);
			}else if(packetID == 0x03) {
				return new SetCompressionPacket(s);
			}else if(packetID == 0x00) {
				return new PacketServerListPingResponse(s);
			}
			
		}
		
//		System.err.println("Invalid packet : " + Integer.toHexString(packetID));
		
		return null;
	}

	public static int getIDFromPacket(Packet packet, boolean isPlaying) {
		
		if(!isPlaying)
		{
			if(packet instanceof PacketHandshake || packet instanceof PacketLoginStart) {
				return 0x00;
			}
		}
		else 
		{
			if(packet instanceof CPacketKeepAlive) {
				return 0x00;
			}else if(packet instanceof CPacketSendChat) {
				return 0x01;
			}else if(packet instanceof CPacketUpdatePosition) {
				return 0x04;
			}else if(packet instanceof CPacketPlayer) {
				return 0x03;		
			}else if(packet instanceof CPacketMove) {
				return 0x06;
			}
			
		}
		
		return -1;
	}
	
}
