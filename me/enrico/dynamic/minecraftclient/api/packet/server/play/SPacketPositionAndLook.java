package me.enrico.dynamic.minecraftclient.api.packet.server.play;

import java.util.EnumSet;
import java.util.Set;

import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.PacketDataSerializer;
import me.enrico.dynamic.minecraftclient.api.player.util.Location;

public class SPacketPositionAndLook extends Packet {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private Set<PositionFlags> flags;
	
	public SPacketPositionAndLook(PacketDataSerializer s) {
		super(s);
	}
	
	@Override
	public void readPacketData() throws Exception {
        this.x = data.readDouble();
        this.y = data.readDouble();
        this.z = data.readDouble();
        this.yaw = data.readFloat();
        this.pitch = data.readFloat();
        this.flags = PositionFlags.read(data.readUnsignedByte());
	}
	
	@Override
	public void writePacketData() throws Exception {}
	
	public Set<PositionFlags> getFlags() {
		return flags;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public Location getLocation() {
		return new Location(x, y, z, yaw, pitch);
	}
	
	public static enum PositionFlags {
        X(0),
        Y(1),
        Z(2),
        Y_ROT(3),
        X_ROT(4);

        private int flag;

        private PositionFlags(int flag)
        {
            this.flag=flag;
        }

        private int bit()
        {
            return 1 << this.flag;
        }

        private boolean checkFlag(int p_180054_1_)
        {
            return (p_180054_1_ & this.bit()) == this.bit();
        }

		public static Set<PositionFlags> read(short iflag) {
            Set<PositionFlags> set = EnumSet.noneOf(PositionFlags.class);

            for (PositionFlags flag : values())
            {
                if (flag.checkFlag(iflag))
                {
                    set.add(flag);
                }
            }

            return set;
		}
		
	}


	
}
