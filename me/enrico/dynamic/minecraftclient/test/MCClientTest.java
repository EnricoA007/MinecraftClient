package me.enrico.dynamic.minecraftclient.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.enrico.dynamic.minecraftclient.api.MinecraftClient;
import me.enrico.dynamic.minecraftclient.api.player.GameProfile;
import me.enrico.dynamic.minecraftclient.api.player.standard.DefaultPlayer;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.EventHandler;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.EventListener;
import me.enrico.dynamic.minecraftclient.api.player.standard.event.types.ChatMessageReceivedEvent;
import java.util.UUID;

public class MCClientTest {

	private static DefaultPlayer p;
	private static Gson gson = new GsonBuilder().create();

	public static void main(String[] args) {

//47 = 1.8
//754 = 1.16.5

		MinecraftClient client = MinecraftClient.createClient(754, "45.142.114.227", 1337, DefaultPlayer.class, new GameProfile("§eRechner", UUID.randomUUID()));
		p = (DefaultPlayer) client.getPlayer();
		p.eventFactory.registerListener(new Listener());

	}

	public static class Listener implements EventListener{
		@EventHandler
		public void onChat(ChatMessageReceivedEvent e){
			/**
			 *{"extra":[{"color":"gray","text":"["},{"color":"gold","text":"0"},{"color":"gray","text":"] "},{"color":"dark_gray","text":"["},{"color":"gray","text":"Member"},{"color":"dark_gray","text":"] "},{"color":"gray","text":"Enrico_A007"},{"color":"dark_gray","text":":"},{"color":"white","text":" 1"}],"text":""}
			 */

			JsonObject obj = gson.fromJson(e.getMessage(), JsonObject.class);
			JsonArray extra = obj.get("extra").getAsJsonArray();
			String text = extra.get(0).getAsString();

			String[] args = text.split(" ");

			if (args[1].equalsIgnoreCase("calc")) {
				p.sendMessage("Das Ergebnis der Rechnung ist: " + DefaultPlayer.Calculator.evaluate(args[2]));
			}



		}
	}
	

}
