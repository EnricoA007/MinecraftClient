package me.enrico.dynamic.minecraftclient.api.packet.handlers;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.enrico.dynamic.minecraftclient.api.MinecraftClient;
import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;
import me.enrico.dynamic.minecraftclient.api.packet.PacketFactory;

public class PacketDecoder extends ByteToMessageDecoder {
	
	private MinecraftClient client;
	
	public PacketDecoder(MinecraftClient client) {
		this.client=client;
	}
	
	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> packets) throws Exception {
		if(in.readableBytes() == 0) return;
		
		PacketDataSerializer s = new PacketDataSerializer(in);
		int packetID = s.readVarInt();
		
		Packet packet = PacketFactory.getPacketFromID(packetID, s, this.client.isPlayingMode());
		if(packet == null) {
			s.readBytes(s.readableBytes());
			return;
//			throw new IOException("Bad packet id " + packetID);
		}
		
		packet.readPacketData();
		
		packets.add(packet);
	}
	
}
