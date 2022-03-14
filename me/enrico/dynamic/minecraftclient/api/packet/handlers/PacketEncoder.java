package me.enrico.dynamic.minecraftclient.api.packet.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.enrico.dynamic.minecraftclient.api.MinecraftClient;
import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketFactory;
import me.enrico.dynamic.minecraftclient.api.packet.legacy.LegacyPacketWrite;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
	
	private MinecraftClient client;
	
	public PacketEncoder(MinecraftClient client) {
		this.client=client;
	}
	
	@Override
	public void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
		if(!(packet instanceof LegacyPacketWrite)) {
			//Write Packet ID
			packet.getPacketDataSerializer().writeVarInt(PacketFactory.getIDFromPacket(packet, this.client.isPlayingMode()));		
		}
		
		//Call the packet to write the packet data into the byte array
		packet.writePacketData();
		//Write packet data into the output
		out.writeBytes(packet.getPacketDataSerializer());
	}
	
}
