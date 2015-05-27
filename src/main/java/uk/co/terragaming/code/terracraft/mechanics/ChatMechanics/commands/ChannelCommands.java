package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChannelManager;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.Channel;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.OptArg;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class ChannelCommands {

	@Command({"channels", "channel", "chan"})
	@CommandDescription("Get a list of channels you are part of.")
	public void onChannelsCommand(Player sender, Language language, @OptArg("") Channel channel){
		if (channel != null){
			onJoinCommand(sender, language, channel);
			return;
		}

		UUID uuid = sender.getUniqueId();
		List<String> names = Lists.newArrayList();
		for (Channel c : ChannelManager.getChannels()){
			if (c.getJoinedPlayers().contains(uuid)){
				names.add("<h>" + c.getDisplayName(sender));
			}
		}
		
		Channel defaultChannel = AccountMechanics.getInstance().getRegistry().getAccount(sender).getActiveChannel();
		
		if (names.size() > 0){
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "channelListPreText")));
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Txt.implodeCommaAndDot(names, "<r>")));
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "chatDefaultChannel"), defaultChannel.getName()));
		} else {
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "chatNoChannels")));
		}
	}
	
	@Command("join")
	@CommandDescription("Join a chat channel")
	public void onJoinCommand(Player sender, Language language, Channel channel){
		if (channel.canJoin(sender)){
			channel.add(sender);
			if (channel.getId() == 0){
				ChannelManager.getChannel("yell").add(sender);
				ChannelManager.getChannel("emote").add(sender);
			}
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "chatChannelJoin"), channel.getDisplayName(sender)));
		} else {
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "ChatNoChannelPerm"), channel.getDisplayName(sender)));
		}
	}
	
	@Command("leave")
	@CommandDescription("Leave a chat channel")
	public void onLeaveCommand(Player sender, Language language, Channel channel){
		channel.remove(sender);
		if (channel.getId() == 0){
			ChannelManager.getChannel("yell").remove(sender);
			ChannelManager.getChannel("emote").remove(sender);
		}
		
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "chatChannelLeave"), channel.getDisplayName(sender)));
	}
}
