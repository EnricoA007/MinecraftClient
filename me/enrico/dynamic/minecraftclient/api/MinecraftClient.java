package me.enrico.dynamic.minecraftclient.api;

import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import me.enrico.dynamic.minecraftclient.api.packet.Packet;
import me.enrico.dynamic.minecraftclient.api.packet.handlers.PacketDecoder;
import me.enrico.dynamic.minecraftclient.api.packet.handlers.PacketEncoder;
import me.enrico.dynamic.minecraftclient.api.packet.handlers.PacketHandler;
import me.enrico.dynamic.minecraftclient.api.packet.handlers.PacketPrepender;
import me.enrico.dynamic.minecraftclient.api.packet.handlers.PacketSplitter;
import me.enrico.dynamic.minecraftclient.api.packet.handlers.event.IPacketReceiver;
import me.enrico.dynamic.minecraftclient.api.player.GameProfile;
import me.enrico.dynamic.minecraftclient.api.player.Player;

public class MinecraftClient {
	
	public static Gson gson = new GsonBuilder().create();
	
	private int protocol,port;
	private String address;
	private Class<? extends Player> playerclass;
	private Player player;
	private Channel channel;
	private boolean playingMode = false;

	protected MinecraftClient(int protocol, String address, int port, Class<? extends Player> playerclass, GameProfile profile) {
		if(playerclass.equals(Player.class)) {
			throw new RuntimeException("The player class can't be 'Player.class', because it's not for superclass uses.");
		}
			
		this.protocol=protocol;
		this.address=address;
		this.port=port;
		this.playerclass=playerclass;
		
		try {
			this.player=(Player) this.playerclass.getConstructors()[0].newInstance(profile,this);
		} catch (Exception e) {
			throw new RuntimeException("Error while creating player object, mostly the error is that the Constructor was modified in the player object.");
		}
		
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(new NioEventLoopGroup());
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.channel(NioSocketChannel.class);
		MinecraftClient client = this;
		
		bootstrap.handler(new ChannelInitializer<Channel>() {
			public void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30));
				pipeline.addLast("splitter", (ChannelHandler)new PacketSplitter());
				pipeline.addLast("decoder", (ChannelHandler)new PacketDecoder(client));
				pipeline.addLast("prepender", (ChannelHandler)new PacketPrepender());
				pipeline.addLast("encoder", (ChannelHandler)new PacketEncoder(client));
				pipeline.addLast("packet_handler", (ChannelHandler)new PacketHandler(player.getReceiver()));
			}
		});
		
		try {
			this.channel = bootstrap.connect(address,port).sync().channel();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			player.callFinish();
		} catch (IllegalAccessException e) {}
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
	
	public int getProtocol() {
		return protocol;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Class<? extends Player> getPlayerClass() {
		return playerclass;
	}
	
	public IPacketReceiver getReceiver() {
		return player.getReceiver();
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	/**
	 * This will call automatically, please don't call this method If you don't know what you are doing
	 */
	@Deprecated
	public void enablePlayingMode() {
		this.playingMode = true;
	}
	
	public boolean isPlayingMode() {
		return this.playingMode;
	}

	
	public void sendPacket(Packet packet) {	
		this.channel.writeAndFlush(packet);
	}
	
	/**
	 * Create a minecraft client
	 * 
	 * @param protocol The minecraft version protocol id (List: <a href="https://wiki.vg/Protocol_version_numbers">https://wiki.vg/Protocol_version_numbers</a>)
	 * @param address The server address
	 * @param port The port of the server address
	 * @param playerclass The class that extends <b>Player.class</b> The superclass is not allowed. This is used to create the Player object
	 * @param profile The game profile of the player who will connect on the server
	 * @return The minecraft client
	 */
	public static MinecraftClient createClient(int protocol, String address, int port, Class<? extends Player> playerclass, GameProfile profile) {
		try {
			new Socket(address,port).close();
		}catch(Exception ex) {
			throw new ServerNotOnline(address, port);
		}
		return new MinecraftClient(protocol, address, port, playerclass, profile);
	}
	
	public static class AsyncCall {
		
		private Callback callback;
		private long seconds;
		private boolean started = false;
		private Thread timerThread = null;
		private boolean stopped = false;
		private CallbackObject<Boolean> condition;
		
		/**
		 * Create an async call
		 * @param callback The method which will be called when the time is over 
		 * @param ms The milliseconds until it stop
		 */
		public AsyncCall(Callback callback, long ms) {
			this.callback=callback;
			this.seconds=ms;
		}
		
		/**
		 * Create an async call
		 * @param callback The method which will be called
		 * @param condition When this is true, the callback will be called
		 * @param refreshIntervall How long the thread is waiting (in ms) for recalling the callback object
		 */
		public AsyncCall(Callback callback, int refreshIntervall, CallbackObject<Boolean> condition) {
			this.callback=callback;
			this.condition=condition;
			this.seconds=refreshIntervall;
		}
		
		public void startTimer() {
			if(started == false && timerThread == null) started = true;
			
			if(condition == null) {
				timerThread = new Thread("Timer # " + hashCode()) {
					
					@Override
					public void run() {
						long waited = 0;

						while(!this.isInterrupted() || !stopped) {
							try {
								Thread.sleep(1);
								waited++;
								
								if(waited == seconds) {
									timerThread = null;
									started = false;
									waited = 0;
									callback.callback();
									break;
								}
								
							}catch(Exception e) {}
						}
						
					}
					
				};	
				timerThread.setDaemon(true);
				timerThread.start();
			}else {
				timerThread = new Thread("Timer # " + hashCode()) {
					
					@Override
					public void run() {
						if(seconds > 1) {
							while((!this.isInterrupted() || !stopped) && !condition.callback()) {
								try {
									Thread.sleep(seconds);
								}catch(Exception ex) {
									
								}
							}
						}else {
							while(!this.isInterrupted() || !stopped && !condition.callback()) {}	
						}
						
						timerThread = null;
						started = false;
						callback.callback();
						this.interrupt();
						
					}
					
				};
				timerThread.setDaemon(true);
				timerThread.start();
			}
			
		}
		
		public Callback getCallback() {
			return callback;
		}
		
		public long getSeconds() {
			return seconds;
		}
		
		public boolean isStopped() {
			return stopped;
		}
		
		public void stop() {
			this.stopped = true;
		}
		
		@FunctionalInterface
		public static interface Callback {
			void callback();
		}
		
		@FunctionalInterface
		public static interface CallbackObject<E> {
			E callback();
		}
		
	}
	
	private static class ServerNotOnline extends RuntimeException {

		private static final long serialVersionUID = 1L;
		private String address;
		private int port;
		
		public ServerNotOnline(String address, int port){
			this.port=port;this.address=address;
		}
	
		@Override
		public String getMessage() {
			return "The Server \"" + address + "\" with port " + port + " is offline";
		}
		
	}


}
