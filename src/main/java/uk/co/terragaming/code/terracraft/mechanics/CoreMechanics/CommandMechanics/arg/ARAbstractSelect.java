package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.arg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.exceptions.CommandException;
import uk.co.terragaming.code.terracraft.utils.Txt;

public abstract class ARAbstractSelect<T> extends ARAbstract<T> {
	
	// -------------------------------------------- //
	// CONSTANT
	// -------------------------------------------- //
	
	public static final int LIST_COUNT_MAX = 50;
	
	// -------------------------------------------- //
	// ABSTRACT
	// -------------------------------------------- //
	
	public abstract T select(String str, CommandSender sender) throws CommandException;
	
	public abstract Collection<String> altNames(CommandSender sender);
	
	public boolean canList(CommandSender sender) {
		return true;
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public T read(String arg, CommandSender sender) throws CommandException {
		T result = this.select(arg, sender);
		
		if (result != null)
			return result;
		
		CommandException exception = new CommandException();
		exception.addMessage("<b>No %s matches \"<h>%s<b>\".", getTypeName(), arg);
		
		if (this.canList(sender)) {
			Collection<String> names = this.altNames(sender);
			
			// Try Levenshtein
			List<String> matches = this.getMatchingAltNames(arg, sender, this.getMaxLevenshteinDistanceForArg(arg));
			
			if (names.isEmpty()) {
				exception.addMessage("<i>Note: There is no %s available.", getTypeName());
			} else if (!matches.isEmpty() && matches.size() < LIST_COUNT_MAX) {
				// For some reason the arguments doesn't get parsed.
				String suggest = Txt.parse(Txt.implodeCommaAnd(matches, "<i>, <h>", " <i>or <h>"), !(sender instanceof Player));
				exception.addMessage("<i>Did you mean <h>%s<i>?", suggest);
			} else if (names.size() > LIST_COUNT_MAX) {
				exception.addMessage("<i>More than %d alternatives available.", LIST_COUNT_MAX);
			} else {
				String format = "<h>%s";
				String comma = "<i>, ";
				String and = " <i>or ";
				String dot = "<i>.";
				exception.addMessage("<i>Use %s", Txt.implodeCommaAndDot(names, format, comma, and, dot));
			}
		}
		
		throw exception;
	}
	
	public List<String> getMatchingAltNames(String arg, CommandSender sender, int maxLevenshteinDistance) {
		if (arg == null)
			return Collections.emptyList(); // For some apparent reason this is
											// required.
		arg = arg.toLowerCase();
		
		// Try Levenshtein
		List<String> matches = new ArrayList<String>();
		
		for (String alias : this.altNames(sender)) {
			String aliaslc = alias.toLowerCase();
			int distance = StringUtils.getLevenshteinDistance(arg, aliaslc);
			if (distance > maxLevenshteinDistance) {
				continue;
			}
			matches.add(alias);
		}
		return matches;
	}
	
	public int getMaxLevenshteinDistanceForArg(String arg) {
		if (arg == null)
			return 0; // For some apparent reason this is required.
		if (arg.length() <= 1)
			return 0; // When dealing with 1 character aliases, there is way too
						// many options.
		if (arg.length() <= 7)
			return 1; // 1 is default.
			
		return 2; // If it were 8 characters or more, we end up here. Because
					// many characters allow for more typos.
	}
	
}