package uk.co.terragaming.code.terracraft.mechanics.ChatMechanicsV1.listeners;
//package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.listeners;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//import org.bukkit.Bukkit;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.EventPriority;
//import org.bukkit.event.Listener;
//import org.bukkit.event.player.AsyncPlayerChatEvent;
//import org.bukkit.event.player.PlayerChatTabCompleteEvent;
//
//import uk.co.terragaming.code.terracraft.enums.ChatChannel;
//import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
//import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChatChannelManager;
//import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
//import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
//import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
//import uk.co.terragaming.code.terracraft.utils.Txt;
//
//import com.google.common.collect.Lists;
//
//public class ChatEventListenerOld implements Listener{
//
//	@EventHandler(priority = EventPriority.HIGHEST)
//	public void onChat (AsyncPlayerChatEvent event){
//		Player player = event.getPlayer();
//		String msg = event.getMessage();
//		
//		if (msg.startsWith("@")){
//			String channelName = msg.split(" ")[0].substring(1);
//			switch(channelName){
//			case "local":
//			
//				break;
//				
//			case "global":
//				
//				break;
//				
//			case "party":
//				
//				break;
//				
//			case "staff":
//				
//				break;
//				
//			case "yell":
//				
//				break;
//				
//			case "emote":
//				
//				break;
//				
//			case "ooc":
//				
//				break;
//			default:
//				List<String> names = getPMNames(player);
//				
//				if (names.contains("@" + channelName)){
//					Player reciever = Bukkit.getPlayer(channelName);
//					if (player != null) return;
//					
//					
//				}
//				
//				// TODO: Show Error Message
//				
//				break;
//			}
//		
//			// TODO: Channel Specific Stuff
//			
//		} else {
//			ChatChannel channel = ChatChannelManager.getDefaultChannel(player);
//			sendMessage(channel, player, msg.substring(msg.indexOf(" ")));
//		}
//		event.setCancelled(true);
//	}
//	
//	private void sendMessage(ChatChannel channel, Player sender, String message){
//		
//		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
//		
//		String name = sender.getName();
//		
//		if (registry.hasAccount(sender)){
//			Account account = AccountMechanics.getInstance().getRegistry().getAccount(sender);
//			name = account.getTerraTag();
//			
//			if (!channel.equals(ChatChannel.OOC)){
//				Character character = account.getActiveCharacter();
//				if (character != null){
//					name = character.getName();
//				}
//			}
//		}
//		
//		sender.sendMessage(Txt.parse("<%s><%s> %s", channel.toString(), name, message));
//		
//		for (Player player : Bukkit.getOnlinePlayers()){
//			if (player.equals(sender)) continue;
//			if (ChatChannelManager.isInChannel(player, channel)){
//				// TODO: Channel Specific stuff...
//				
//				player.sendMessage(Txt.parse("<%s><%s> %s", channel.toString(), name, message));
//				
//			}
//		}
//	}
//	
//	private void sendPrivateMessage(Player sender, Player reciever, String message){
//		
//	}
//	
//	@EventHandler
//	public void onChatTab(PlayerChatTabCompleteEvent event){
//		event.getChatMessage();
//		if (event.getLastToken().startsWith("@") && event.getChatMessage().split(" ").length == 1){
//			event.getTabCompletions().clear();
//			event.getTabCompletions().addAll(getFilteredTabList(getChannelTabList(event.getPlayer()), event.getLastToken()));
//		}
//	}
//	
//	private List<String> getChannelTabList(Player chatter){
//		List<String> channels = Lists.newArrayList();
//		channels.add("@local");
//		channels.add("@global");
//		channels.add("@party");
//		channels.add("@staff");
//		channels.add("@yell");
//		channels.add("@emote");
//		
//		channels.addAll(getPMNames(chatter));
//		return channels;
//	}
//	
//	private List<String> getPMNames(Player chatter){
//		List<String> channels = Lists.newArrayList();
//		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
//		
//		for (Player player : Bukkit.getOnlinePlayers()){
//			if (player.equals(chatter)) continue;
//			channels.add("@" + player.getName());
//			if (registry.hasAccount(player)){
//				Account account = registry.getAccount(player);
//				if (!account.getTerraTag().equalsIgnoreCase(player.getName())){
//					channels.add("@" + account.getTerraTag());
//				}
//				
//				Character character = account.getActiveCharacter();
//				if (character != null){
//					channels.add("@" + character.getName().replace(" ", "_"));
//				}
//			}
//		}
//		return channels;
//	}
//	
//	private Player getPlayerFromName(String name){
//		Player player = Bukkit.getPlayer(name);
//		if (player != null) return player;
//		return player;
//		
//	}
//}
