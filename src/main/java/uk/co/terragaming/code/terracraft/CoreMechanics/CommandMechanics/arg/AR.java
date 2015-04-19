package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics.arg;

import java.util.Collection;
import java.util.List;

import org.bukkit.command.CommandSender;

import uk.co.terragaming.code.terracraft.CoreMechanics.TerraException;

public interface AR<T> {

	// Human friendly Name
	public String getTypeName();
	
	// Read
	public T read(String arg, CommandSender sender) throws TerraException;
	public T read(CommandSender sender) throws TerraException;
	public T read(String arg) throws TerraException;
	public T read() throws TerraException;
	
	// Valid (used for arbitrary argument order)
	public boolean isValid(String arg, CommandSender sender);
	
	// Tab List
	public Collection<String> getTabList(CommandSender sender, String arg);
	public List<String> getTabListFiltered(CommandSender sender, String arg);
}
