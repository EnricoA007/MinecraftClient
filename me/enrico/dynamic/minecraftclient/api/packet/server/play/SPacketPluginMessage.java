package me.enrico.dynamic.minecraftclient.api.packet.server.play;

import java.io.IOException;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;

public class SPacketPluginMessage extends Packet {

	private String channel;
	private PacketDataSerializer data;
	
	public SPacketPluginMessage(PacketDataSerializer s) {
		super(s);
	}
	
	@Override
	public void readPacketData() throws IOException {
        this.channel = getPacketDataSerializer().c(20);
        int i = data.readableBytes();

        if (i >= 0 && i <= 1048576)
        {
            this.data = new PacketDataSerializer(data.readBytes(i));
        }
        else
        {
            throw new IOException("Payload may not be larger than 1048576 bytes");
        }
	}
	
	
	@Override
	public void writePacketData() {}
	
	public String getChannel() {
		return channel;
	}
	
	public PacketDataSerializer getData() {
		return data;
	}
	
}
