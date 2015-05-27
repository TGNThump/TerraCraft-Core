package uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.commands;

import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.Channel;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class ChannelCommands {

	@Command("join")
	@CommandDescription("Join a chat channel")
	public void onJoinCommand(Player sender, Language language, Channel channel){
		if (channel.canJoin(sender)){
			channel.add(sender);
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "chatChannelJoin"), channel.getDisplayName(sender)));
		} else {
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "ChatNoChannelPerm"), channel.getDisplayName(sender)));
		}
	}
	
	@Command("leave")
	@CommandDescription("Leave a chat channel")
	public void onLeaveCommand(Player sender, Language language, Channel channel){
		if (channel.contains(sender)){
			channel.remove(sender);
		}
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "chatChannelLeave"), channel.getDisplayName(sender)));
	}
}
