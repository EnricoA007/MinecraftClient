package me.enrico.dynamic.minecraftclient.api.player.standard;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import io.netty.channel.Channel;
import me.enrico.dynamic.minecraftclient.api.MinecraftClient;
import me.enrico.dynamic.minecraftclient.api.MinecraftClient.AsyncCall;
import me.enrico.dynamic.minecraftclient.api.packet.client.CPacketKeepAlive;
import me.enrico.dynamic.minecraftclient.api.packet.client.CPacketSendChat;
import me.enrico.dynamic.minecraftclient.api.packet.handlers.compression.NettyCompressionDecoder;
import me.enrico.dynamic.minecraftclient.api.packet.handlers.compression.NettyCompressionEncoder;
import me.enrico.dynamic.minecraftclient.api.packet.handlers.event.IPacketReceiver;
import me.enrico.dynamic.minecraftclient.api.packet.handshake.PacketHandshake;
import me.enrico.dynamic.minecraftclient.api.packet.handshake.PacketHandshake.ConnectionState;
import me.enrico.dynamic.minecraftclient.api.packet.handshake.PacketLoginStart;
import me.enrico.dynamic.minecraftclient.api.packet.server.login.LoginSuccess;
import me.enrico.dynamic.minecraftclient.api.packet.server.login.SetCompressionPacket;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketChatMessage;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketDifficultyChange;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketJoinGame;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketKeepAlive;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketPositionAndLook;
import me.enrico.dynamic.minecraftclient.api.packet.server.play.SPacketUpdateHealth;
import me.enrico.dynamic.minecraftclient.api.player.GameProfile;
import me.enrico.dynamic.minecraftclient.api.player.Player;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.AccessibleEvents;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.EventFactory;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.types.ChatMessageReceivedEvent;
import me.enrico.dynamic.minecraftclient.api.player.util.Difficulty;
import me.enrico.dynamic.minecraftclient.api.player.util.Dimension;
import me.enrico.dynamic.minecraftclient.api.player.util.Gamemode;
import me.enrico.dynamic.minecraftclient.api.player.util.ILocation;
import me.enrico.dynamic.minecraftclient.api.player.util.Location;

public class DefaultPlayer extends Player {
	
	private static Gson gson = new GsonBuilder().create();
	public EventFactory eventFactory = new EventFactory(AccessibleEvents.access());
	private SPacketJoinGame join;
	private SPacketUpdateHealth health;
	private Location loc;
	
	public DefaultPlayer(GameProfile profile, MinecraftClient client) {
		super(profile, client);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public IPacketReceiver getReceiver() {
		return packet -> {
			
			/**
			 * Check for the LOGIN Packets, and If Login succeed, the state will be changed from "LOGIN" to "PLAY"
			 */
			if(packet instanceof LoginSuccess) {
				LoginSuccess p = (LoginSuccess) packet;
				this.getClient().enablePlayingMode();
				this.setProfile(new GameProfile(p.getUsername(), UUID.fromString(p.getUUID())));
			}else if(packet instanceof SetCompressionPacket) {
				setCompressionTreshold(((SetCompressionPacket)packet).getThreshold(), this.getClient().getChannel());
			}
			
			/**
			 * PLAY Packet Managment
			 */

			if(packet instanceof SPacketKeepAlive) {
				int id = ((SPacketKeepAlive)packet).getID();
				this.sendPacket(new CPacketKeepAlive(id));
			}else if(packet instanceof SPacketJoinGame) {
				if(this.join != null) this.join = (SPacketJoinGame) packet;
			}else if(packet instanceof SPacketChatMessage) {
				
				// {"extra":["\u003cRoboter\u003e Der Roboter ist gejoint :) Hallo!"],"text":""}
				
				SPacketChatMessage chat = (SPacketChatMessage) packet;
				this.eventFactory.callEvent(new ChatMessageReceivedEvent(this.getClient(), chat.getJsonMessage(), gson));
				
			}else if(packet instanceof SPacketDifficultyChange) {
				this.join.setDifficulty(((SPacketDifficultyChange)packet).getDifficulty());
			}else if(packet instanceof SPacketPositionAndLook) {
				this.loc = ((SPacketPositionAndLook)packet).getLocation();
			}else if(packet instanceof SPacketUpdateHealth) {
				this.health = (SPacketUpdateHealth)packet;
			}
			
		};
	}

	public void overwriteEventFactory(EventFactory factory) {
		this.eventFactory=factory;
	}
	
	public void sendMessage(String message) {
		this.sendPacket(new CPacketSendChat(message));
	}
	
	@Override
	public void callFinish() throws IllegalAccessException {
		MinecraftClient client = getClient();
		sendPacket(new PacketHandshake(client.getProtocol(), (client.getAddress() + "\000" + client.getAddress() + "\000" + client.getPlayer().getProfile().getUUID().toString()), client.getPort(), ConnectionState.LOGIN));
		sendPacket(new PacketLoginStart(getProfile().getUsername())); 
		
		new AsyncCall(() -> {
			try {
				super.callFinish();
			} catch (IllegalAccessException e) {}
		}, 50).startTimer();
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public int getEntityID() {
		return this.join.getEntityID();
	}
	
	public Gamemode getGameMode() {
		return this.join.getGamemode();
	}
	
	public Difficulty getDifficulty() {
		return this.join.getDifficulty();
	}
	
	public Dimension getDimension() {
		return this.join.getDimension();
	}
	
	public int getMaxPlayers() {
		return this.join.getMaxPlayers();
	}
	
	public String getLevelType() {
		return this.join.getLevelType();
	}
	
	public boolean isReducedDebugInfo() {
		return this.join.isReducedDebugInfo();
	}
	
	public boolean isHardcore() {
		return this.join.isHardcore();
	}
	
	public float getHealth() {
		return this.health.getHealth();
	}
	
	public int getFood() {
		return this.health.getFood();
	}
	
	public int getSaturation() {
		return this.health.getSaturation();
	}
	
	private void setCompressionTreshold(int treshold, Channel channel)
	    {
	        if (treshold >= 0)
	        {
	            if (channel.pipeline().get("decompress") instanceof NettyCompressionDecoder)
	            {
	                ((NettyCompressionDecoder)channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
	            }
	            else
	            {
	            	channel.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(treshold));
	            }

	            if (channel.pipeline().get("compress") instanceof NettyCompressionEncoder)
	            {
	                ((NettyCompressionEncoder)channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
	            }
	            else
	            {
	               channel.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(treshold));
	            }
	        }
	        else
	        {
	            if (channel.pipeline().get("decompress") instanceof NettyCompressionDecoder)
	            {
	                channel.pipeline().remove("decompress");
	            }

	            if (channel.pipeline().get("compress") instanceof NettyCompressionEncoder)
	            {
	                channel.pipeline().remove("compress");
	            }
	        }
	    }
	
	public static class Calculator {
		public static double evaluate(String e) {
			
			e=e.replace('+', 'A');
			e=e.replace('-', 'B');
			e=e.replace('*', 'C');
			e=e.replace('/', 'D');
			
			String[] add = e.split("A");
			
			for(int i = 1; i<add.length; i++) {
				if(!isSigned(add[i])) {
					String z =add[i];
					add[i] = "A" + z;
				}
			}
			
			String exp = "";for(String b : add) {exp += b + " ";}exp=exp.trim();
			
			add = exp.split("B");
			
			for(int i = 1; i<add.length; i++) {
				if(!isSigned(add[i])) {
					String z =add[i];
					add[i] = "B" + z;
				}
			}
			
			exp = "";for(String b : add) {exp += b + " ";}exp=exp.trim();
			
			add = exp.split("C");
			
			for(int i = 1; i<add.length; i++) {
				if(!isSigned(add[i])) {
					String z =add[i];
					add[i] = "C" + z;
				}
			}
			
			exp = "";for(String b : add) {exp += b + " ";}exp=exp.trim();
			
			add = exp.split("D");
			
			for(int i = 1; i<add.length; i++) {
				if(!isSigned(add[i])) {
					String z =add[i];
					add[i] = "D" + z;
				}
			}
			
			exp = "";for(String b : add) {exp += b + " ";}exp=exp.trim();
			
			exp=exp.replace('A', '+');
			exp=exp.replace('B', '-');
			exp=exp.replace('C', '*');
			exp=exp.replace('D', '/');
			
			String[] content = exp.split(" ");
			
			double num = Double.parseDouble(content[0]);
			
			for(int i = 1; i<content.length; i++) {
				String a = content[i];
				String b = a.substring(1,a.length());
				if(a.startsWith("*")) {
					num *=Double.parseDouble(b);
				}else if(a.startsWith("/")) {
					num /=Double.parseDouble(b);
				}else if(a.startsWith("+")) {
					num +=Double.parseDouble(b);
				}else if(a.startsWith("-")) {
					num -=Double.parseDouble(b);
				}else {
					num +=Double.parseDouble(b);
				}
			}
			
			return num;
			
		}
		
		private static boolean isSigned(String numb) {
			return(numb.startsWith("A") || numb.startsWith("B") || numb.startsWith("C") || numb.startsWith("D"));
		}
	}

	/**
	 * Send a move packet to the server
	 * @param x position added/subtracted
	 * @param y position added/subtracted
	 * @param z position added/subtracted
	 * @param onGround is the player jumping?
	 * <p>now shipping with slab support</p>
	 */
	public void movePlayer(double x, double y, double z, float yaw, float pitch, boolean onGround) {
		ILocation l = ((ILocation)this.loc);
		l.setX(l.getX()+x);
		l.setY(l.getY()+y);
		l.setZ(l.getZ()+z);
	}
//
	@Override
	public void disconnect() {
		this.getClient().getChannel().close().syncUninterruptibly();
	}
}
