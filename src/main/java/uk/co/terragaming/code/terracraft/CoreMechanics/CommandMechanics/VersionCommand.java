package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.annotations.CommandUsage;
import uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.arg.CommandArgument;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class VersionCommand {

	public static final String NOT_SPECIFIED = Txt.parse("<em><silver>not specified");
	
	@Command({"version","ver"})
	@CommandDescription("Display TerraCraft Version")
	@CommandUsage("/version")
	public void onVersionCommand(CommandSender sender, CommandArgument[] args){
		PluginDescriptionFile pdf = TerraCraft.Plugin().getDescription();
		
		String name = pdf.getName();
		String version = pdf.getVersion();
		String website = pdf.getWebsite();
		
		String description = pdf.getDescription();
		if (description != null) description = Txt.parse("<i>"+description);
		
		String authors = null;
		List<String> authorList = pdf.getAuthors();
		if (authorList != null && authorList.size() > 0)
		{
			authors = Txt.implodeCommaAndDot(authorList, "<aqua>%s", "<i> ", " <i>and ", "");
			authors = Txt.parse(authors);
		}
		
		this.sendTitle(sender);
		this.sendEntry(sender, "name", name);
		this.sendEntry(sender, "version", version);
		this.sendEntry(sender, "website", website);
		this.sendEntry(sender, "authors", authors);
		this.sendEntry(sender, "description", description);
	}
	
	public void sendTitle(CommandSender sender)
	{
		sender.sendMessage(Txt.titleize("Plugin Version & Information"));
	}
	
	public void sendEntry(CommandSender sender, String key, String value)
	{
		sender.sendMessage(Txt.parse("<pink>%s: <aqua>%s", Txt.upperCaseFirst(key), value == null ? NOT_SPECIFIED : value));
	}
}
