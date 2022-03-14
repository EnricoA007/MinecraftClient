package me.enrico.dynamic.minecraftclient.api.packet.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.handlers.event.IPacketReceiver;

public class PacketHandler extends SimpleChannelInboundHandler<Packet> {

	private IPacketReceiver receiver;
	
	public PacketHandler(IPacketReceiver receiver) {
		this.receiver=receiver;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, Packet arg1) throws Exception {
		this.receiver.receive(arg1);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext arg0, Throwable arg1) throws Exception {
//		arg1.printStackTrace();
	}

}
