package uk.co.terragaming.code.terracraft.CoreMechanics.CommandMechanics;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.CoreMechanics.TerraException;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class CommandExecutor implements TabExecutor{

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command bukkitCommand, String alias, String[] arguments) {
		Command rootCommand = CommandRegistry.getRootCommand(bukkitCommand);
		Entry<Command, List<String>> commandEntry = CommandRegistry.getChildCommandWithArgs(rootCommand, arguments);
		Command command = commandEntry.getKey();
		List<String> args = commandEntry.getValue();
		
		Integer currentArgIndex = args.size() - 1;
		String arg = args.get(currentArgIndex);
		
		Integer paramIndex = 0;
		Integer argIndex = 0;
		
		// For each command parameter ...
		for (CommandParameter param : command.getParams()){
			// ... if the parameter is not special ...
			if (param.isSpecial()){ paramIndex++; continue; }
			
			// ... and we are autocompleting the current parameter ...
			if (currentArgIndex == argIndex){
				// If the parameter is a tag ...
				if (param.isTag()){
					// ... and the argument starts with '-' ...
					if (args.get(argIndex).startsWith("-")){
						// TODO: AutoComplete Available Tags
						break;
					}
					// ... otherwise, continue we don't want to autocomplete tags unless the '-' is already present.
					paramIndex++;
					continue;
				}
				
				// If the parameter is not a tag ...
				// ... autocomplete the current parameter.
				return param.getArgReader().getTabListFiltered(sender, arg);
			}
			
			// If we are autocompleting the current parameter ...
			// ... and the parameter is a tag ...
			if (param.isTag()){
				// If the argument is equal the the tag, increase the argIndex ...
				if (args.get(argIndex).equals("-" + param.getName())){
					argIndex++;
				}
				// and then increase the paramIndex and continue.
				paramIndex++;
				continue;
			}
			
			// ... or if the parameter is a normal argument ...
			// ... increase the arg and param index and continue.
			argIndex++;
			paramIndex++;
		}
		
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String label, String[] bukkitArgs) {
		Command command = CommandRegistry.getRootCommand(bukkitCommand.getName());
		List<String> commandArgs = Lists.newArrayList(bukkitArgs);
		
		// If the command has at least one argument ...
		if (bukkitArgs.length > 0){
			// ... Grab the subcommand by argument recursively, parsing the remaining arguments ...
			Entry<Command, List<String>> result = CommandRegistry.getChildCommandWithArgs(command, bukkitArgs);
			
			command = result.getKey();
			commandArgs = result.getValue();
		}
		
		// ... then get the command handler and method ...
		Object handler = command.getHandler();
		Method method = command.getMethod();
		
		if (handler == null | method == null){
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Unknown command. Type \"<c>/" + command.getPath() + " help<r>\" for help."));
			return true;
		}
		
		// ... and the arguments from argumentReaders ...
		Object[] arguments;
		try {
			arguments = getArguments(command, commandArgs, sender);
		} catch (TerraException e) {
			for(String string : Txt.wrap(e.getMessages())){ 
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + string)); 
			} 
			return true; 
		}
		if (arguments == null) return true;
		
		// ... and invoke the method with the arguments.
		try { method.invoke(handler, arguments); }
		catch (Exception e){
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] An error occurred while trying to process the command"));
			e.printStackTrace();
		}
		return true;
	}

	private Object[] getArguments(Command command, List<String> commandArgs, CommandSender sender) throws TerraException {
		CommandParameter[] params = command.getParams();
		Object[] args = new Object[params.length];
		
		int paramIndex = 0;
		int argIndex = 0;
		
		int argLength = commandArgs.size();
		int paramLength = params.length;

		// For each parameter ...
		for (CommandParameter param : params){
			
			// If the parameter is special ...
			if (param.isSpecial()){
				if (param.getType().isAssignableFrom(CommandSender.class)){
					if (param.getType().equals(Player.class) && !(sender instanceof Player)){
						TerraException ex = new TerraException();
						ex.addMessage(Txt.parse("<b>%s", "This command must be run as a Player."));
						throw ex;
					}
					
					if (param.getType().equals(ConsoleCommandSender.class) && !(sender instanceof ConsoleCommandSender)){
						TerraException ex = new TerraException();
						ex.addMessage(Txt.parse("<b>%s", "This command must be run from the Console."));
						throw ex;
					}
					
					args[paramIndex] = sender;
				} else if (param.getType().equals(Command.class)){
					args[paramIndex] = command;
				}
			
				paramIndex++;
				paramLength--;
				continue;
			}
			
			// ... or if the parameter is a tag ...
			if (param.isTag()){
				if (argIndex < commandArgs.size()){
					if (!param.isNamed() && commandArgs.get(argIndex).startsWith("-")){argIndex++; argLength--; sender.sendMessage(Txt.parse("<b>Command Tags are disabled")); }
					else if (commandArgs.get(argIndex).equals("-" + param.getName())){
						args[paramIndex] = true;
						paramIndex++;
						argIndex++;
						argLength--;
						paramLength--;
						continue;
					}
				}
				args[paramIndex] = false;
				paramIndex++;
				paramLength--;
				continue;
			}
			
			Object arg = null;
			
			// ... otherwise, if the argIndex > the number of arguments ...
			if (argIndex < commandArgs.size()){
				
				// ... attempt to read the argument.
				arg = param.getArgReader().read(commandArgs.get(argIndex), sender);
				argIndex++;
				
			// ... or if the parameter is optional ...
			} else if (param.isOptional()){
				
				// ... attempt to read the default value.
				arg = param.getArgReader().read(param.getDefaultValue(), sender);
				
			// ... otherwise ...
			} else {
				
				// ... throw a new TerraException - Incorrect Command Usage ...
				TerraException ex = new TerraException();
				ex.addMessage(Txt.parse("<b>%s", "Incorrect Command Usage"));
				ex.addMessage(Txt.parse(command.getUsage()));
				throw ex;
			}
			
			args[paramIndex] = arg;
			paramIndex++;
		}
		
		if (argLength > paramLength){
			TerraException ex = new TerraException();
			ex.addMessage(Txt.parse("<b>%s", "Incorrect Command Usage"));
			ex.addMessage(Txt.parse(command.getUsage()));
			throw ex;
		}
		return args;
	}

}
