package me.enrico.dynamic.minecraftclient.api.packet.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;

public class PacketPrepender extends MessageToByteEncoder<ByteBuf> {
 
   public void encode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) throws Exception {
    int i = paramByteBuf1.readableBytes();
    int j = PacketDataSerializer.a(i);
    if (j > 3)
      throw new IllegalArgumentException("unable to fit " + i + " into " + '\003'); 
    PacketDataSerializer packetDataSerializer = new PacketDataSerializer(paramByteBuf2);
    packetDataSerializer.ensureWritable(j + i);
    packetDataSerializer.b(i);
    packetDataSerializer.writeBytes(paramByteBuf1, paramByteBuf1.readerIndex(), i);
  }

}

