package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import java.util.Collection;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.exceptions.CommandException;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChannelManager;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.Channel;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.PartyChannel;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.WhisperChannel;

import com.google.common.collect.Lists;

public class ARChannel extends ARAbstractSelect<Channel> implements ARAllAble<Channel> {
	
	private static ARChannel i = new ARChannel();
	
	public static ARChannel get() {
		return i;
	}
	
	// Override
	
	@Override
	public Channel select(String arg, CommandSender sender) throws CommandException {
		
		return ChannelManager.getChannel(arg);
		
	}
	
	@Override
	public Collection<String> altNames(CommandSender sender) {
		List<String> ret = Lists.newArrayList();
		
		for (Channel channel : ChannelManager.getChannels()) {
			if (channel instanceof WhisperChannel) {
				continue;
			}
			if (channel instanceof PartyChannel) {
				continue;
			}
			if (sender instanceof Player) {
				if (!channel.canJoin((Player) sender)) {
					continue;
				}
				ret.add(channel.getDisplayName((Player) sender));
			} else {
				ret.add(channel.getName());
			}
		}
		
		return ret;
	}
	
	@Override
	public Collection<String> getTabList(CommandSender sender, String arg) {
		List<String> ret = Lists.newArrayList();
		
		for (Channel channel : ChannelManager.getChannels()) {
			if (channel instanceof WhisperChannel) {
				continue;
			}
			
			if (channel instanceof PartyChannel) {
				continue;
			}
			if (sender instanceof Player) {
				if (!channel.canJoin((Player) sender)) {
					continue;
				}
				ret.add(channel.getDisplayName((Player) sender));
			} else {
				ret.add(channel.getName());
			}
		}
		
		return ret;
	}
	
	@Override
	public Collection<Channel> getAll(CommandSender sender) {
		return ChannelManager.getChannels();
	}
}
