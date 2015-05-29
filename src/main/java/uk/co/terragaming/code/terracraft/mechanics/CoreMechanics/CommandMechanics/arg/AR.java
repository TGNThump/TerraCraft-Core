package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import java.util.Collection;
import java.util.List;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.exceptions.CommandException;

public interface AR<T> {
	
	// Human friendly Name
	public String getTypeName();
	
	// Read
	public T read(String arg, CommandSender sender) throws CommandException;
	
	public T read(CommandSender sender) throws CommandException;
	
	public T read(String arg) throws CommandException;
	
	public T read() throws CommandException;
	
	// Valid (used for arbitrary argument order)
	public boolean isValid(String arg, CommandSender sender);
	
	// Tab List
	public Collection<String> getTabList(CommandSender sender, String arg);
	
	public List<String> getTabListFiltered(CommandSender sender, String arg);
}
