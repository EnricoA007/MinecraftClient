package me.enrico.dynamic.minecraftclient.api.packet.handlers;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;

public class PacketSplitter extends ByteToMessageDecoder {

	 public void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception {
	    paramByteBuf.markReaderIndex();
	    byte[] arrayOfByte = new byte[3];
	    for (byte b = 0; b < arrayOfByte.length; b++) {
	      if (!paramByteBuf.isReadable()) {
	        paramByteBuf.resetReaderIndex();
	        return;
	      } 
	      arrayOfByte[b] = paramByteBuf.readByte();
	      if (arrayOfByte[b] >= 0) {
	        PacketDataSerializer packetDataSerializer = new PacketDataSerializer(Unpooled.wrappedBuffer(arrayOfByte));
	        try {
	          int i = packetDataSerializer.e();
	          if (paramByteBuf.readableBytes() < i) {
	            paramByteBuf.resetReaderIndex();
	            return;
	          } 
	          paramList.add(paramByteBuf.readBytes(i));
	          return;
	        } finally {
	          packetDataSerializer.release();
	        } 
	      } 
	    } 
	    throw new CorruptedFrameException("length wider than 21-bit");
	  }


}