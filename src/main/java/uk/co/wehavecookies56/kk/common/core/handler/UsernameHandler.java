package uk.co.wehavecookies56.kk.common.core.handler;

import com.google.common.collect.HashBiMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Toby on 28/03/2016.
 */
public class UsernameHandler {

    private final HashBiMap<String, HashBiMap<String, String>> usernamePropsRegistry;
    private final URL url;

    public UsernameHandler(String url) throws MalformedURLException {
        this.usernamePropsRegistry = HashBiMap.create(new HashMap<String, HashBiMap<String, String>>());
        this.url = new URL(url);
    }

    public void load() {
        try {
            JsonParser parser = new JsonParser();
            JsonArray names = (JsonArray)parser.parse(new JsonReader(new InputStreamReader(this.url.openStream())));

            for (int i = 0; i < names.size(); i++) {
                JsonObject obj = (JsonObject) names.get(i);
                String username = obj.get("username").getAsString();
                String nick = obj.get("nickname").getAsString();
                String prefix = obj.get("prefix").getAsString();
                String nameformat = obj.get("nameformat").getAsString();
                String prefixformat = obj.get("prefixformat").getAsString();
                String chatformat = obj.get("chatformat").getAsString();
                HashBiMap<String, String> props = HashBiMap.create(new HashMap<String, String>());
                props.put("nickname", nick);
                props.put("prefix", prefix);
                props.put("nameformat", nameformat);
                props.put("prefixformat", prefixformat);
                props.put("chatformat", chatformat);
                this.usernamePropsRegistry.put(username, props);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init (FMLPreInitializationEvent event) throws MalformedURLException {

        UsernameHandler names = new UsernameHandler("https://www.dropbox.com/s/z4uj87utdzjbyee/names.json?raw=1");

        MinecraftForge.EVENT_BUS.register(names);

        names.usernamePropsRegistry.clear();
        names.load();
    }

    @SubscribeEvent
    public void PlayerNameFormatting(PlayerEvent.NameFormat event)
    {
        if (this.usernamePropsRegistry.containsKey(event.getUsername()))
            event.setDisplayname(this.usernamePropsRegistry.get(event.getUsername()).get("nickname"));
    }

    @SubscribeEvent
    public void ServerChat(ServerChatEvent event)
    {
        if (event.getPlayer() != null)
        {
            EntityPlayer player = event.getPlayer();
            event.setCanceled(true);

            List players = player.worldObj.playerEntities;
            String nameFormat = null;
            String chatFormat = null;
            String prefixFormat = null;
            String prefix = null;

            for (int i = 0; i < players.size(); i++)
            {
                if (this.usernamePropsRegistry.containsKey(event.getUsername())) {
                	
                    if (this.usernamePropsRegistry.get(event.getUsername()).containsKey("nameformat")){
                        nameFormat = this.usernamePropsRegistry.get(event.getUsername()).get("nameformat");
	                    if(nameFormat.contains("Â"))
	                    	nameFormat = nameFormat.substring(1, nameFormat.length());
                    }
                    else nameFormat = "§f";
                   
                    if (this.usernamePropsRegistry.get(event.getUsername()).containsKey("chatformat")){
                        chatFormat = this.usernamePropsRegistry.get(event.getUsername()).get("chatformat");
                        if(chatFormat.contains("Â"))
                        	chatFormat = chatFormat.substring(1, chatFormat.length());
                    }
	                else chatFormat = "§f";
                   
	                if (this.usernamePropsRegistry.get(event.getUsername()).containsKey("prefixformat")){
                        prefixFormat = this.usernamePropsRegistry.get(event.getUsername()).get("prefixformat");
                        if(prefixFormat.contains("Â"))
                        	prefixFormat = prefixFormat.substring(1, prefixFormat.length());
	                }
                    else prefixFormat = "§f";
                    
                    if (this.usernamePropsRegistry.get(event.getUsername()).containsKey("prefix")){
                        prefix = this.usernamePropsRegistry.get(event.getUsername()).get("prefix");
	                    if(prefix.contains("Â"))
	                    	prefix = prefix.substring(1, prefix.length());
	                }
                    else prefix = "";
                } else {
                    nameFormat = chatFormat = prefixFormat = "§f";
                    prefix = "";
                }
                EntityPlayer target = (EntityPlayer) players.get(i);
                String prefixWithFormat = "";
                if (!prefix.isEmpty()) prefixWithFormat =  "[" + prefixFormat + prefix + TextFormatting.WHITE + "] ";
                String nameWithFormat = TextFormatting.WHITE + "<" + nameFormat + player.getDisplayNameString() + TextFormatting.WHITE + "> ";
                target.addChatComponentMessage(new TextComponentTranslation(prefixWithFormat + nameWithFormat + chatFormat + event.getMessage()));
            }
        }
    }
}
