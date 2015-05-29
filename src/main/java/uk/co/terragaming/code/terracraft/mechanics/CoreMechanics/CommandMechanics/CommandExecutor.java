package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.exceptions.CommandException;
import uk.co.terragaming.code.terracraft.exceptions.TerraException;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.utils.ChatUtils;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

import com.google.common.collect.Lists;

public class CommandExecutor implements TabExecutor {
	
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
		for (CommandParameter param : command.getParams()) {
			// ... if the parameter is not special ...
			if (param.isSpecial()) {
				paramIndex++;
				continue;
			}
			
			// ... and we are autocompleting the current parameter ...
			if (currentArgIndex == argIndex) {
				// If the parameter is a tag ...
				if (param.isTag()) {
					// ... and the argument starts with '-' ...
					if (args.get(argIndex).startsWith("-")) {
						List<String> ret = Lists.newArrayList();
						
						for (CommandParameter p : command.getParams()) {
							ret.add("-" + p.getName());
						}
						
						return ChatUtils.getFilteredTabList(ret, args.get(argIndex));
					}
					// ... otherwise, continue we don't want to autocomplete
					// tags unless the '-' is already present.
					paramIndex++;
					continue;
				}
				
				// If the parameter is not a tag ...
				// ... autocomplete the current parameter.
				return param.getArgReader().getTabListFiltered(sender, arg);
			}
			
			// If we are autocompleting the current parameter ...
			// ... and the parameter is a tag ...
			if (param.isTag()) {
				// If the argument is equal the the tag, increase the argIndex
				// ...
				if (args.get(argIndex).equals("-" + param.getName())) {
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
		if (bukkitArgs.length > 0) {
			// ... Grab the subcommand by argument recursively, parsing the
			// remaining arguments ...
			Entry<Command, List<String>> result = CommandRegistry.getChildCommandWithArgs(command, bukkitArgs);
			
			command = result.getKey();
			commandArgs = result.getValue();
		}
		
		Language lang = Language.ENGLISH;
		
		if (sender instanceof Player) {
			AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
			if (registry.hasAccount((Player) sender)) {
				lang = registry.getAccount((Player) sender).getLanguage();
			}
		}
		
		// ... then get the command handler and method ...
		Object handler = command.getHandler();
		Method method = command.getMethod();
		
		if (handler == null | method == null) {
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(lang, "commandUnknown"), command.getPath(), !(sender instanceof Player)));
			return true;
		}
		
		// ... and the arguments from argumentReaders ...
		Object[] arguments;
		try {
			arguments = getArguments(command, commandArgs, sender, lang);
		} catch (CommandException e) {
			for (String string : Txt.wrap(e.getMessages())) {
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + string, !(sender instanceof Player)));
			}
			return true;
		}
		if (arguments == null)
			return true;
		
		// ... and invoke the method with the arguments.
		try {
			method.invoke(handler, arguments);
		} catch (IllegalAccessException e) {
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(lang, "commandError"), !(sender instanceof Player)));
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(lang, "commandError"), !(sender instanceof Player)));
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.getCause().printStackTrace();
			if (e.getCause().getClass().isAssignableFrom(TerraException.class)) {
				CommandException ex = (CommandException) e.getCause();
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + ex.getMessages().get(0), !(sender instanceof Player)));
			} else {
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(lang, "commandError"), !(sender instanceof Player)));
			}
		}
		return true;
	}
	
	private Object[] getArguments(Command command, List<String> commandArgs, CommandSender sender, Language lang) throws CommandException {
		CommandParameter[] params = command.getParams();
		Object[] args = new Object[params.length];
		
		int paramIndex = 0;
		int argIndex = 0;
		
		int argLength = commandArgs.size();
		int paramLength = params.length;
		
		// For each parameter ...
		for (CommandParameter param : params) {
			
			// If the parameter is special ...
			if (param.isSpecial()) {
				if (param.getType().equals(CommandSender.class) || param.getType().equals(Player.class) || param.getType().equals(ConsoleCommandSender.class)) {
					if (param.getType().equals(Player.class) && !(sender instanceof Player)) {
						CommandException ex = new CommandException();
						ex.addMessage("<b>%s", Lang.get(lang, "commandRequiresPlayer", false));
						throw ex;
					}
					
					if (param.getType().equals(ConsoleCommandSender.class) && !(sender instanceof ConsoleCommandSender)) {
						CommandException ex = new CommandException();
						ex.addMessage("<b>%s", Lang.get(lang, "commandRequiresConsole", true));
						throw ex;
					}
					
					args[paramIndex] = sender;
				} else if (param.getType().equals(Command.class)) {
					args[paramIndex] = command;
				} else if (param.getType().equals(Language.class)) {
					args[paramIndex] = lang;
				}
				
				paramIndex++;
				paramLength--;
				continue;
			}
			
			// ... or if the parameter is a tag ...
			if (param.isTag()) {
				if (argIndex < commandArgs.size()) {
					if (!param.isNamed() && commandArgs.get(argIndex).startsWith("-")) {
						argIndex++;
						argLength--;
						sender.sendMessage(Txt.parse("<b>Command Tags are disabled", !(sender instanceof Player)));
					} else if (commandArgs.get(argIndex).equals("-" + param.getName())) {
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
			if (argIndex < commandArgs.size()) {
				
				// ... attempt to read the argument.
				arg = param.getArgReader().read(commandArgs.get(argIndex), sender);
				argIndex++;
				
				// ... or if the parameter is optional ...
			} else if (param.isOptional()) {
				// ... attempt to read the default value.
				if (param.getDefaultValue().equals("")) {
					arg = null;
				} else {
					arg = param.getArgReader().read(param.getDefaultValue(), sender);
				}
				// ... otherwise ...
			} else {
				
				// ... throw a new TerraException - Incorrect Command Usage ...
				CommandException ex = new CommandException();
				ex.addMessage("<b>%s", Lang.get(lang, "commandIncorrectUsage", true));
				ex.addMessage(command.getUsage());
				throw ex;
			}
			
			args[paramIndex] = arg;
			paramIndex++;
		}
		
		if (argLength > paramLength) {
			CommandException ex = new CommandException();
			ex.addMessage("<b>%s", Lang.get(lang, "commandIncorrectUsage", true));
			ex.addMessage(command.getUsage());
			throw ex;
		}
		return args;
	}
	
}
