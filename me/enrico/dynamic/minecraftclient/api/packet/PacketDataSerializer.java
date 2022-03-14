package me.enrico.dynamic.minecraftclient.api.packet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import me.enrico.dynamic.minecraftclient.api.player.util.Location;

public class PacketDataSerializer extends ByteBuf {
  private final ByteBuf a;
  
  public PacketDataSerializer(ByteBuf bytebuf) {
    this.a = bytebuf;
  }
  
  public ByteBuf buf() {
	  return a;
  }
  
  public Location readLocation() {
	  long val = readLong();
	  double x = val >> 38;
	  double y = val & 0xFFF;
	  double z = (val << 26 >> 38);
	  return new Location(x, y, z);
  }
  
  public void writeLocation(Location loc) {
	  long val = ((((int)loc.getX() & 0x3FFFFFF) << 38) | (((int)loc.getZ() & 0x3FFFFFF) << 12) | ((int)loc.getY() & 0xFFF));
	  writeLong(val);
  }
  
  public int readVarInt() {
	    int value = 0;
	    int bitOffset = 0;
	    byte currentByte;
	    do {
	        if (bitOffset == 35) throw new RuntimeException("VarInt is too big");

	        currentByte = readByte();
	        value |= (currentByte & 0b01111111) << bitOffset;

	        bitOffset += 7;
	    } while ((currentByte & 0b10000000) != 0);

	    return value;
	}
  
  public void writeVarInt(int value) {
	    do {
	        byte currentByte = (byte) (value & 0b01111111);

	        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
	        value >>>= 7;
	        if (value != 0) currentByte |= 0b10000000;

	        writeByte(currentByte);
	    } while (value != 0);
	}
  
  public static int a(int i) {
    for (int j = 1; j < 5; j++) {
      if ((i & -1 << j * 7) == 0)
        return j; 
    } 
    return 5;
  }
  
  public void a(byte[] abyte) {
    b(abyte.length);
    writeBytes(abyte);
  }
  
  public byte[] a() {
    byte[] abyte = new byte[e()];
    readBytes(abyte);
    return abyte;
  }
  
  
  @SuppressWarnings("unchecked")
  public <T extends Enum<T>> T a(Class<T> oclass) {
    return (T)((Enum[])oclass.getEnumConstants())[e()];
  }
  
  public void a(Enum<?> oenum) {
    b(oenum.ordinal());
  }
  
  public int e() {
    byte b0;
    int i = 0;
    int j = 0;
    do {
      b0 = readByte();
      i |= (b0 & Byte.MAX_VALUE) << j++ * 7;
      if (j > 5)
        throw new RuntimeException("VarInt too big"); 
    } while ((b0 & 0x80) == 128);
    return i;
  }
  
  public long f() {
    byte b0;
    long i = 0L;
    int j = 0;
    do {
      b0 = readByte();
      i |= (b0 & Byte.MAX_VALUE) << j++ * 7;
      if (j > 10)
        throw new RuntimeException("VarLong too big"); 
    } while ((b0 & 0x80) == 128);
    return i;
  }
  
  public void a(UUID uuid) {
    writeLong(uuid.getMostSignificantBits());
    writeLong(uuid.getLeastSignificantBits());
  }
  
  public UUID g() {
    return new UUID(readLong(), readLong());
  }
  
  public void b(int i) {
    while ((i & 0xFFFFFF80) != 0) {
      writeByte(i & 0x7F | 0x80);
      i >>>= 7;
    } 
    writeByte(i);
  }
  
  public void b(long i) {
    while ((i & 0xFFFFFFFFFFFFFF80L) != 0L) {
      writeByte((int)(i & 0x7FL) | 0x80);
      i >>>= 7L;
    } 
    writeByte((int)i);
  }
  
 
  public String c(int i) {
    int j = e();
    if (j > i * 4)
      throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + j + " > " + (i * 4) + ")"); 
    if (j < 0)
      throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!"); 
    String s = new String(readBytes(j).array(), StandardCharsets.UTF_8);
    if (s.length() > i)
      throw new DecoderException("The received string length is longer than maximum allowed (" + j + " > " + i + ")"); 
    return s;
  }
  
  public PacketDataSerializer writeString(String s) {
    byte[] abyte = s.getBytes(StandardCharsets.UTF_8);
    if (abyte.length > 32767)
      throw new EncoderException("String too big (was " + s.length() + " bytes encoded)"); 
    b(abyte.length);
    writeBytes(abyte);
    return this;
  }
  
  public int capacity() {
    return this.a.capacity();
  }
  
  public ByteBuf capacity(int i) {
    return this.a.capacity(i);
  }
  
  public int maxCapacity() {
    return this.a.maxCapacity();
  }
  
  public ByteBufAllocator alloc() {
    return this.a.alloc();
  }
  
  public ByteOrder order() {
    return this.a.order();
  }
  
  public ByteBuf order(ByteOrder byteorder) {
    return this.a.order(byteorder);
  }
  
  public boolean isDirect() {
    return this.a.isDirect();
  }
  
  public int readerIndex() {
    return this.a.readerIndex();
  }
  
  public ByteBuf readerIndex(int i) {
    return this.a.readerIndex(i);
  }
  
  public int writerIndex() {
    return this.a.writerIndex();
  }
  
  public ByteBuf writerIndex(int i) {
    return this.a.writerIndex(i);
  }
  
  public ByteBuf setIndex(int i, int j) {
    return this.a.setIndex(i, j);
  }
  
  public int readableBytes() {
    return this.a.readableBytes();
  }
  
  public int writableBytes() {
    return this.a.writableBytes();
  }
    
 
  public ByteBuf clear() {
    return this.a.clear();
  }
  
  public ByteBuf markReaderIndex() {
    return this.a.markReaderIndex();
  }
  
  public ByteBuf resetReaderIndex() {
    return this.a.resetReaderIndex();
  }
  
  public ByteBuf markWriterIndex() {
    return this.a.markWriterIndex();
  }
  
  public ByteBuf resetWriterIndex() {
    return this.a.resetWriterIndex();
  }
  
  public ByteBuf discardReadBytes() {
    return this.a.discardReadBytes();
  }

  
  public boolean getBoolean(int i) {
    return this.a.getBoolean(i);
  }
  
  public byte getByte(int i) {
    return this.a.getByte(i);
  }
  
  public short getUnsignedByte(int i) {
    return this.a.getUnsignedByte(i);
  }
  
  public short getShort(int i) {
    return this.a.getShort(i);
  }
  
  public int getUnsignedShort(int i) {
    return this.a.getUnsignedShort(i);
  }
  
  public int getMedium(int i) {
    return this.a.getMedium(i);
  }
  
  public int getUnsignedMedium(int i) {
    return this.a.getUnsignedMedium(i);
  }
  
  public int getInt(int i) {
    return this.a.getInt(i);
  }
  
  public long getUnsignedInt(int i) {
    return this.a.getUnsignedInt(i);
  }
  
  public long getLong(int i) {
    return this.a.getLong(i);
  }
  
  public char getChar(int i) {
    return this.a.getChar(i);
  }
  
  public float getFloat(int i) {
    return this.a.getFloat(i);
  }
  
  public double getDouble(int i) {
    return this.a.getDouble(i);
  }
  
  public ByteBuf getBytes(int i, ByteBuf bytebuf) {
    return this.a.getBytes(i, bytebuf);
  }
  
  public ByteBuf getBytes(int i, ByteBuf bytebuf, int j) {
    return this.a.getBytes(i, bytebuf, j);
  }
  
  public ByteBuf getBytes(int i, ByteBuf bytebuf, int j, int k) {
    return this.a.getBytes(i, bytebuf, j, k);
  }
  
  public ByteBuf getBytes(int i, byte[] abyte) {
    return this.a.getBytes(i, abyte);
  }
  
  public ByteBuf getBytes(int i, byte[] abyte, int j, int k) {
    return this.a.getBytes(i, abyte, j, k);
  }
  
  public ByteBuf getBytes(int i, ByteBuffer bytebuffer) {
    return this.a.getBytes(i, bytebuffer);
  }
  
  public ByteBuf getBytes(int i, OutputStream outputstream, int j) throws IOException {
    return this.a.getBytes(i, outputstream, j);
  }
  
  public int getBytes(int i, GatheringByteChannel gatheringbytechannel, int j) throws IOException {
    return this.a.getBytes(i, gatheringbytechannel, j);
  }
  
  public ByteBuf setBoolean(int i, boolean flag) {
    return this.a.setBoolean(i, flag);
  }
  
  public ByteBuf setByte(int i, int j) {
    return this.a.setByte(i, j);
  }
  
  public ByteBuf setShort(int i, int j) {
    return this.a.setShort(i, j);
  }
  
  public ByteBuf setMedium(int i, int j) {
    return this.a.setMedium(i, j);
  }
  
  public ByteBuf setInt(int i, int j) {
    return this.a.setInt(i, j);
  }
  
  public ByteBuf setLong(int i, long j) {
    return this.a.setLong(i, j);
  }
  
  public ByteBuf setChar(int i, int j) {
    return this.a.setChar(i, j);
  }
  
  public ByteBuf setFloat(int i, float f) {
    return this.a.setFloat(i, f);
  }
  
  public ByteBuf setDouble(int i, double d0) {
    return this.a.setDouble(i, d0);
  }
  
  public ByteBuf setBytes(int i, ByteBuf bytebuf) {
    return this.a.setBytes(i, bytebuf);
  }
  
  public ByteBuf setBytes(int i, ByteBuf bytebuf, int j) {
    return this.a.setBytes(i, bytebuf, j);
  }
  
  public ByteBuf setBytes(int i, ByteBuf bytebuf, int j, int k) {
    return this.a.setBytes(i, bytebuf, j, k);
  }
  
  public ByteBuf setBytes(int i, byte[] abyte) {
    return this.a.setBytes(i, abyte);
  }
  
  public ByteBuf setBytes(int i, byte[] abyte, int j, int k) {
    return this.a.setBytes(i, abyte, j, k);
  }
  
  public ByteBuf setBytes(int i, ByteBuffer bytebuffer) {
    return this.a.setBytes(i, bytebuffer);
  }
  
  public int setBytes(int i, InputStream inputstream, int j) throws IOException {
    return this.a.setBytes(i, inputstream, j);
  }
  
  public int setBytes(int i, ScatteringByteChannel scatteringbytechannel, int j) throws IOException {
    return this.a.setBytes(i, scatteringbytechannel, j);
  }
  
  public ByteBuf setZero(int i, int j) {
    return this.a.setZero(i, j);
  }
  
  public boolean readBoolean() {
    return this.a.readBoolean();
  }
  
  public byte readByte() {
    return this.a.readByte();
  }
  
  public short readUnsignedByte() {
    return this.a.readUnsignedByte();
  }
  
  public short readShort() {
    return this.a.readShort();
  }
  
  public int readUnsignedShort() {
    return this.a.readUnsignedShort();
  }
  
  public int readMedium() {
    return this.a.readMedium();
  }
  
  public int readUnsignedMedium() {
    return this.a.readUnsignedMedium();
  }
  
  public int readInt() {
    return this.a.readInt();
  }
  
  public long readUnsignedInt() {
    return this.a.readUnsignedInt();
  }
  
  public long readLong() {
    return this.a.readLong();
  }
  
  public char readChar() {
    return this.a.readChar();
  }
  
  public float readFloat() {
    return this.a.readFloat();
  }
  
  public double readDouble() {
    return this.a.readDouble();
  }
  
  public ByteBuf readBytes(int i) {
    return this.a.readBytes(i);
  }
  
  public ByteBuf readSlice(int i) {
    return this.a.readSlice(i);
  }
  
  public ByteBuf readBytes(ByteBuf bytebuf) {
    return this.a.readBytes(bytebuf);
  }
  
  public ByteBuf readBytes(ByteBuf bytebuf, int i) {
    return this.a.readBytes(bytebuf, i);
  }
  
  public ByteBuf readBytes(ByteBuf bytebuf, int i, int j) {
    return this.a.readBytes(bytebuf, i, j);
  }
  
  public ByteBuf readBytes(byte[] abyte) {
    return this.a.readBytes(abyte);
  }
  
  public ByteBuf readBytes(byte[] abyte, int i, int j) {
    return this.a.readBytes(abyte, i, j);
  }
  
  public ByteBuf readBytes(ByteBuffer bytebuffer) {
    return this.a.readBytes(bytebuffer);
  }
  
  public ByteBuf readBytes(OutputStream outputstream, int i) throws IOException {
    return this.a.readBytes(outputstream, i);
  }
  
  public int readBytes(GatheringByteChannel gatheringbytechannel, int i) throws IOException {
    return this.a.readBytes(gatheringbytechannel, i);
  }
  
  public ByteBuf skipBytes(int i) {
    return this.a.skipBytes(i);
  }
  
  public ByteBuf writeBoolean(boolean flag) {
    return this.a.writeBoolean(flag);
  }
  
  public ByteBuf writeByte(int i) {
    return this.a.writeByte(i);
  }
  
  public ByteBuf writeShort(int i) {
    return this.a.writeShort(i);
  }
  
  public ByteBuf writeMedium(int i) {
    return this.a.writeMedium(i);
  }
  
  public ByteBuf writeInt(int i) {
    return this.a.writeInt(i);
  }
  
  public ByteBuf writeLong(long i) {
    return this.a.writeLong(i);
  }
  
  public ByteBuf writeChar(int i) {
    return this.a.writeChar(i);
  }
  
  public ByteBuf writeFloat(float f) {
    return this.a.writeFloat(f);
  }
  
  public ByteBuf writeDouble(double d0) {
    return this.a.writeDouble(d0);
  }
  
  public ByteBuf writeBytes(ByteBuf bytebuf) {
    return this.a.writeBytes(bytebuf);
  }
  
  public ByteBuf writeBytes(ByteBuf bytebuf, int i) {
    return this.a.writeBytes(bytebuf, i);
  }
  
  public ByteBuf writeBytes(ByteBuf bytebuf, int i, int j) {
    return this.a.writeBytes(bytebuf, i, j);
  }
  
  public ByteBuf writeBytes(byte[] abyte) {
    return this.a.writeBytes(abyte);
  }
  
  public ByteBuf writeBytes(byte[] abyte, int i, int j) {
    return this.a.writeBytes(abyte, i, j);
  }
  
  public ByteBuf writeBytes(ByteBuffer bytebuffer) {
    return this.a.writeBytes(bytebuffer);
  }
  
  public int writeBytes(InputStream inputstream, int i) throws IOException {
    return this.a.writeBytes(inputstream, i);
  }
  
  public int writeBytes(ScatteringByteChannel scatteringbytechannel, int i) throws IOException {
    return this.a.writeBytes(scatteringbytechannel, i);
  }
  
  public ByteBuf writeZero(int i) {
    return this.a.writeZero(i);
  }
  
  public int indexOf(int i, int j, byte b0) {
    return this.a.indexOf(i, j, b0);
  }
  
  public int bytesBefore(byte b0) {
    return this.a.bytesBefore(b0);
  }
  
  public int bytesBefore(int i, byte b0) {
    return this.a.bytesBefore(i, b0);
  }
  
  public int bytesBefore(int i, int j, byte b0) {
    return this.a.bytesBefore(i, j, b0);
  }


  
  public ByteBuf copy() {
    return this.a.copy();
  }
  
  public ByteBuf copy(int i, int j) {
    return this.a.copy(i, j);
  }
  
  public ByteBuf slice() {
    return this.a.slice();
  }
  
  public ByteBuf slice(int i, int j) {
    return this.a.slice(i, j);
  }
  
  public ByteBuf duplicate() {
    return this.a.duplicate();
  }
  
  public ByteBuffer nioBuffer() {
    return this.a.nioBuffer();
  }
  
  public ByteBuffer nioBuffer(int i, int j) {
    return this.a.nioBuffer(i, j);
  }
  
  public ByteBuffer[] nioBuffers() {
    return this.a.nioBuffers();
  }
  
  public ByteBuffer[] nioBuffers(int i, int j) {
    return this.a.nioBuffers(i, j);
  }
  
  public boolean hasArray() {
    return this.a.hasArray();
  }
  
  public byte[] array() {
    return this.a.array();
  }
  
  public int arrayOffset() {
    return this.a.arrayOffset();
  }
  
  public String toString(Charset charset) {
    return this.a.toString(charset);
  }
  
  public String toString(int i, int j, Charset charset) {
    return this.a.toString(i, j, charset);
  }
  
  public int hashCode() {
    return this.a.hashCode();
  }
  
  public boolean equals(Object object) {
    return this.a.equals(object);
  }
  
  public int compareTo(ByteBuf bytebuf) {
    return this.a.compareTo(bytebuf);
  }
  
  public String toString() {
    return this.a.toString();
  }

@Override
public int refCnt() {
	return this.a.refCnt();
}

@Override
public boolean release() {
	return this.a.release();
}

@Override
public boolean release(int arg0) {
	return this.a.release(arg0);
}

@Override
public ByteBuf discardSomeReadBytes() {
	return this.a.discardSomeReadBytes();
}

@Override
public ByteBuf ensureWritable(int arg0) {
	return this.a.ensureWritable(arg0);
}

@Override
public int ensureWritable(int arg0, boolean arg1) {
	return this.a.ensureWritable(arg0, arg1);
}

@Override
public int forEachByte(ByteBufProcessor arg0) {
	return this.a.forEachByte(arg0);
}

@Override
public int forEachByte(int arg0, int arg1, ByteBufProcessor arg2) {
	return this.a.forEachByte(arg0, arg1, arg2);
}

@Override
public int forEachByteDesc(ByteBufProcessor arg0) {
	return this.a.forEachByteDesc(arg0);
}

@Override
public int forEachByteDesc(int arg0, int arg1, ByteBufProcessor arg2) {
	return this.a.forEachByteDesc(arg0, arg1, arg2);
}

@Override
public boolean hasMemoryAddress() {
	return this.a.hasMemoryAddress();
}

@Override
public ByteBuffer internalNioBuffer(int arg0, int arg1) {
	return this.a.internalNioBuffer(arg0, arg1);
}

@Override
public boolean isReadable() {
	return this.a.isReadable();
}

@Override
public boolean isReadable(int arg0) {
	return this.a.isReadable(arg0);
}

@Override
public boolean isWritable() {
	return this.a.isWritable();
}

@Override
public boolean isWritable(int arg0) {
	return this.a.isWritable(arg0);
}

@Override
public int maxWritableBytes() {
	return this.a.maxWritableBytes();
}

@Override
public long memoryAddress() {
	return this.a.memoryAddress();
}

@Override
public int nioBufferCount() {
	return this.a.nioBufferCount();
}

@Override
public ByteBuf retain() {
	return this.a.retain();
}

@Override
public ByteBuf retain(int arg0) {
	return this.a.retain(arg0);
}

@Override
public ByteBuf unwrap() {
	return this.a.unwrap();
}



}

