package uk.co.terragaming.TerraCore.Util.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.terragaming.TerraCore.Plugin;
import uk.co.terragaming.TerraCore.Util.Collections.Collections;
import uk.co.terragaming.TerraCore.Util.Language.Lang;


public class Text {
	protected static final int PAGEHEIGHT_PLAYER = 9;
	protected static final int PAGEHEIGHT_CONSOLE = 50;
	
	protected static final Map<String, String> chatParseReplacements = new HashMap<String, String>();
	protected static final Map<String, String> consoleParseReplacements = new HashMap<String, String>();
	
	protected static final Pattern chatPattern;
	protected static final Pattern consolePattern;
	
	public static final long millisPerSecond = 1000;
	public static final long millisPerMinute = 60 * millisPerSecond;
	public static final long millisPerHour = 60 * millisPerMinute;
	public static final long millisPerDay = 24 * millisPerHour;
	public static final long millisPerWeek = 7 * millisPerDay;
	public static final long millisPerMonth = 31 * millisPerDay;
	public static final long millisPerYear = 365 * millisPerDay;
	
	public static final Map<String, Long> unitMillis = Collections.map("years", millisPerYear, "months", millisPerMonth, "weeks", millisPerWeek, "days", millisPerDay, "hours", millisPerHour, "minutes", millisPerMinute, "seconds", millisPerSecond);
	
	public static final Set<String> vowel = new LinkedHashSet<String>(Arrays.asList("A", "E", "I", "O", "U", "Y", "Å", "Ä", "Ö", "Æ", "Ø", "a", "e", "i", "o", "u", "y", "å", "ä", "ö", "æ", "ø"));
	
	protected static void addParseReplacement(String key, String chat, String console){
		chatParseReplacements.put(key, chat);
		consoleParseReplacements.put(key, console);
	}
	
	static {
		
		addParseReplacement("<empty>",	"",						"");
		addParseReplacement("<black>",	ChatColor.BLACK,		ConsoleColor.BLACK);
		addParseReplacement("<navy>",	ChatColor.DARK_BLUE,	ConsoleColor.GREEN);
		addParseReplacement("<green>",	ChatColor.DARK_GREEN,	ConsoleColor.GREEN);
		addParseReplacement("<teal>",	ChatColor.DARK_AQUA,	ConsoleColor.CYAN);
		addParseReplacement("<red>",	ChatColor.DARK_RED,		ConsoleColor.RED);
		addParseReplacement("<purple>",	ChatColor.DARK_PURPLE,	ConsoleColor.MAGENTA);
		addParseReplacement("<gold>",	ChatColor.GOLD,			ConsoleColor.YELLOW);
		addParseReplacement("<orange>", ChatColor.GOLD,			ConsoleColor.RED);
		addParseReplacement("<silver>",	ChatColor.GRAY,			ConsoleColor.WHITE);
		addParseReplacement("<gray>",	ChatColor.DARK_GRAY,	ConsoleColor.BLACK);
		addParseReplacement("<grey>",	ChatColor.DARK_GRAY,	ConsoleColor.BLACK);
		addParseReplacement("<blue>",	ChatColor.BLUE,			ConsoleColor.BLUE);
		addParseReplacement("<lime>",	ChatColor.GREEN,		ConsoleColor.GREEN);
		addParseReplacement("<aqua>",	ChatColor.AQUA,			ConsoleColor.CYAN);
		addParseReplacement("<rose>",	ChatColor.RED,			ConsoleColor.RED);
		addParseReplacement("<pink>",	ChatColor.LIGHT_PURPLE,	ConsoleColor.MAGENTA);
		addParseReplacement("<yellow>",	ChatColor.YELLOW,		ConsoleColor.YELLOW);
		addParseReplacement("<white>",	ChatColor.WHITE,		ConsoleColor.WHITE);
		
		addParseReplacement("<magic>",	ChatColor.MAGIC,		"");
		addParseReplacement("<bold>",	ChatColor.BOLD,			"");
		addParseReplacement("<strike>",	ChatColor.STRIKETHROUGH,"");
		addParseReplacement("<under>",	ChatColor.UNDERLINE,	"");
		addParseReplacement("<italic>",	ChatColor.ITALIC,		"");
		
		addParseReplacement("<reset>",	ChatColor.RESET,		ConsoleColor.RESET);
		addParseReplacement("<r>",		ChatColor.RESET,		ConsoleColor.RESET); // Reset
		
		addParseReplacement("<l>",		ChatColor.DARK_AQUA,	ConsoleColor.CYAN); // Logo
		addParseReplacement("<n>",		ChatColor.YELLOW,		ConsoleColor.YELLOW); // Name
		addParseReplacement("<fg>",		ChatColor.GREEN,		ConsoleColor.MAGENTA); // Fealty Group
		
		addParseReplacement("<g>",		ChatColor.GREEN,		ConsoleColor.GREEN); // Good
		addParseReplacement("<b>",		ChatColor.RED,			ConsoleColor.RED); // Bad

		addParseReplacement("<k>",		ChatColor.AQUA,			ConsoleColor.CYAN); // Key
		addParseReplacement("<v>",		ChatColor.LIGHT_PURPLE,	ConsoleColor.RED); // Value
		addParseReplacement("<h>",		ChatColor.YELLOW,		ConsoleColor.YELLOW); // Highlight
		addParseReplacement("<c>",		ChatColor.AQUA,			ConsoleColor.CYAN); // Command
		addParseReplacement("<p>",		ChatColor.DARK_AQUA,	ConsoleColor.CYAN); // Parameter
		
		for (int i = 48; i <= 122; i++) {
			char c = (char) i;
			chatParseReplacements.put("§" + c, "\u00A7" + c);
			chatParseReplacements.put("&" + c, "\u00A7" + c);
			if (i == 57) {
				i = 96;
			}
		}
		
		StringBuilder chatPatternStringBuilder = new StringBuilder();
		StringBuilder consolePatternStringBuilder = new StringBuilder();
		
		chatParseReplacements.keySet().forEach((find)->{
			chatPatternStringBuilder.append("(");
			chatPatternStringBuilder.append(Pattern.quote(find));
			chatPatternStringBuilder.append(")|");
		});
		
		consoleParseReplacements.keySet().forEach((find)->{
			consolePatternStringBuilder.append("(");
			consolePatternStringBuilder.append(Pattern.quote(find));
			consolePatternStringBuilder.append(")|");
		});
		
		
		String chatPatternString = chatPatternStringBuilder.toString();
		String consolePatternString = consolePatternStringBuilder.toString();
		
		chatPatternString = chatPatternString.substring(0, chatPatternString.length() - 1);
		consolePatternString = consolePatternString.substring(0, consolePatternString.length() - 1);
		
		chatPattern = Pattern.compile(chatPatternString);
		consolePattern = Pattern.compile(consolePatternString);
	}
	
	// Parse
	
	public static String stripColor(String string){
		StringBuffer ret = new StringBuffer();
		Matcher matcher = chatPattern.matcher(string);
		
		while (matcher.find()){
			matcher.appendReplacement(ret, "");
		}
		
		matcher.appendTail(ret);
		return ret.toString();
	}
	
	public static String of(boolean console, String string){
		StringBuffer ret = new StringBuffer();
		Matcher matcher = console ? consolePattern.matcher(string) : chatPattern.matcher(string);
		
		while (matcher.find()){
			matcher.appendReplacement(ret, console ? consoleParseReplacements.get(matcher.group(0)) : chatParseReplacements.get(matcher.group(0)));
		}
		matcher.appendTail(ret);
		return ret.toString();
	}
	
	public static String of(String string){
		return of(false, string);
	}
	
	public static String of(boolean console, String string, Object... args){
		Object[] formattedArgs = new Object[args.length];
		for(int i = 0; i < args.length; i ++){
			Object arg = args[i];
			if (arg instanceof String){
				formattedArgs[i] = of(console, (String) arg);
			} else {
				formattedArgs[i] = arg;
			}
		}
		return String.format(of(console, string), formattedArgs); 
	}
	
	public static String of(String string, Object... args){
		return of(false, string, args);
	}
	
	public static ArrayList<String> of(boolean console, Collection<String> strings){
		ArrayList<String> ret = new ArrayList<String>(strings.size());
		strings.forEach((string)->{
			ret.add(of(console, string));
		});
		return ret;
	}
	
	public static ArrayList<String> of(Collection<String> strings){
		return of(false, strings);
	}
	
	// Wrap
	
	public static ArrayList<String> wrap(final String string){
		return new ArrayList<String>(Arrays.asList(string.split("\\r?\\n")));
	}
	
	public static ArrayList<String> wrap(final Collection<String> strings){
		ArrayList<String> ret = new ArrayList<String>();
		strings.forEach((string)->{
			ret.addAll(wrap(string));
		});
		return ret;
	}
	
	// Parse and Wrap
	
	public static ArrayList<String> ofWrap(boolean console, final String string){
		return wrap(of(console, string));
	}
	
	public static ArrayList<String> ofWrap(final String string){
		return ofWrap(false, string);
	}
	
	public static ArrayList<String> ofWrap(boolean console, final Collection<String> strings){
		return wrap(of(console, strings));
	}
	
	public static ArrayList<String> ofWrap(final Collection<String> strings){
		return ofWrap(false, strings);
	}
	
	// Cases
	
	public static String upperCaseFirst(String string){
		if (string == null) return null;
		if (string.length() == 0) return string;
		return string.substring(0,1).toUpperCase() + string.substring(1);
	}
	
	// Repeat
	
	public static String repeat(String string, int times){
		StringBuilder ret = new StringBuilder(times);
		
		for (int i = 0; i < times; i++){
			ret.append(string);
		}
		
		return ret.toString();
	}
	
	// Implode
	
	public static String implode(final Object[] list, final String glue, final String format) {
		StringBuilder ret = new StringBuilder();
		
		for (int i = 0; i < list.length; i++) {
			Object item = list[i];
			String str = item == null ? "NULL" : item.toString();
			
			if (i != 0) {
				ret.append(glue);
			}
			if (format != null) {
				ret.append(String.format(format, str));
			} else {
				ret.append(str);
			}
		}
		
		return ret.toString();
	}
	
	public static String implode(final Object[] list, final String glue) {
		return implode(list, glue, null);
	}
	
	public static String implode(final Collection<? extends Object> coll, final String glue, String format) {
		return implode(coll.toArray(new Object[0]), glue, format);
	}
	
	public static String implode(final Collection<? extends Object> coll, final String glue) {
		return implode(coll, glue, null);
	}
	
	public static String implodeCommaAndDot(final Collection<? extends Object> objects, final String format, final String comma, final String and, final String dot) {
		if (objects.size() == 0)
			return "";
		if (objects.size() == 1)
			return implode(objects, comma, format);
		
		List<Object> ourObjects = new ArrayList<Object>(objects);
		
		String lastItem = ourObjects.get(ourObjects.size() - 1).toString();
		String nextToLastItem = ourObjects.get(ourObjects.size() - 2).toString();
		if (format != null) {
			lastItem = String.format(format, lastItem);
			nextToLastItem = String.format(format, nextToLastItem);
		}
		String merge = nextToLastItem + and + lastItem;
		ourObjects.set(ourObjects.size() - 2, merge);
		ourObjects.remove(ourObjects.size() - 1);
		
		return implode(ourObjects, comma, format) + dot;
	}
	
	public static String implodeCommaAndDot(final Collection<? extends Object> objects, final String comma, final String and, final String dot) {
		return implodeCommaAndDot(objects, null, comma, and, dot);
	}
	
	public static String implodeCommaAnd(final Collection<? extends Object> objects, final String comma, final String and) {
		return implodeCommaAndDot(objects, comma, and, "");
	}
	
	public static String implodeCommaAndDot(final Collection<? extends Object> objects, final String color) {
		return implodeCommaAndDot(objects, color + ", ", color + " and ", color + ".");
	}
	
	public static String implodeCommaAnd(final Collection<? extends Object> objects, final String color) {
		return implodeCommaAndDot(objects, color + ", ", color + " and ", "");
	}
	
	public static String implodeCommaAndDot(final Collection<? extends Object> objects) {
		return implodeCommaAndDot(objects, "");
	}
	
	public static String implodeCommaAnd(final Collection<? extends Object> objects) {
		return implodeCommaAnd(objects, "");
	}
	
	// A-An
	
	public static boolean isVowel(String str) {
		if (str == null || str.length() == 0)
			return false;
		return vowel.contains(str.substring(0, 1));
	}
	
	public static String aan(String noun) {
		return isVowel(noun) ? "an" : "a";
	}
	
	// Material name tools
	
	public static String getNicedEnumString(String str) {
		List<String> parts = new ArrayList<String>();
		for (String part : str.toLowerCase().split("[\\s_]+")) {
			parts.add(upperCaseFirst(part));
		}
		return implode(parts, " ");
	}
	
	public static String getNicedEnum(Object enumObject) {
		return getNicedEnumString(enumObject.toString());
	}
	
	public static String getMaterialName(Material material) {
		return getNicedEnum(material);
	}
	
	// Paging
	
	private final static String titleizeLine = repeat("-", 48);
	private final static int titleizeBalance = -1;
	
	public static String titleize(String str, boolean console) {
		String center = "- [ " + of(console, "<l>") + str + of(console, "<r>") + " ] -";
		int centerlen = ChatColor.stripColor(center).length();
		int pivot = titleizeLine.length() / 2;
		int eatLeft = centerlen / 2 - titleizeBalance;
		int eatRight = centerlen - eatLeft + titleizeBalance;
		
		if (eatLeft < pivot)
			return of(console, "<r>") + titleizeLine.substring(0, pivot - eatLeft) + center + titleizeLine.substring(pivot + eatRight);
		else
			return of(console, "<r>") + center;
	}
	
	public static ArrayList<String> getPage(List<String> lines, int pageHumanBased, String title, boolean console, Locale lang) {
		return getPage(lines, pageHumanBased, title, PAGEHEIGHT_PLAYER, console, lang);
	}
	
	public static ArrayList<String> getPage(List<String> lines, int pageHumanBased, String title, CommandSender sender, Locale lang) {
		return getPage(lines, pageHumanBased, title, sender instanceof Player ? Text.PAGEHEIGHT_PLAYER : Text.PAGEHEIGHT_CONSOLE, !(sender instanceof Player), lang);
	}
	
	public static ArrayList<String> getPage(List<String> lines, int pageHumanBased, String title, int pageheight, boolean console, Locale locale) {
		ArrayList<String> ret = new ArrayList<String>();
		int pageZeroBased = pageHumanBased - 1;
		int pagecount = (int) Math.ceil((double) lines.size() / pageheight);
		
		Lang lang = Plugin.get(Lang.class);
		String prefix = lang.get(locale, "prefix", true, console);
		String noPages = lang.get(locale, "noPages", true, console);
		String invalidPages = lang.get(locale, "invalidPage", true, console, pagecount);
		
		ret.add(prefix + titleize(title + of(console, "<gold>") + " [" + pageHumanBased + "/" + pagecount + "]", console));
		
		if (pagecount == 0) {
			ret.add(prefix + noPages);
			return ret;
		} else if (pageZeroBased < 0 || pageHumanBased > pagecount) {
			ret.add(prefix + invalidPages);
			return ret;
		}
		
		int from = pageZeroBased * pageheight;
		int to = from + pageheight;
		if (to > lines.size()) {
			to = lines.size();
		}
		
		List<String> subList = lines.subList(from, to);
		
		for (int i = 0; i < subList.size(); i++) {
			subList.set(i, prefix + subList.get(i));
		}
		
		ret.addAll(subList);
		
		return ret;
	}
	
	// Describing Time
	
	public static String getTimeDeltaDescriptionRelNow(long millis) {
		String ret = "";
		
		double millisLeft = Math.abs(millis);
		
		List<String> unitCountParts = new ArrayList<String>();
		for (Entry<String, Long> entry : unitMillis.entrySet()) {
			if (unitCountParts.size() == 3) {
				break;
			}
			String unitName = entry.getKey();
			long unitSize = entry.getValue();
			long unitCount = (long) Math.floor(millisLeft / unitSize);
			if (unitCount < 1) {
				continue;
			}
			millisLeft -= unitSize * unitCount;
			unitCountParts.add(unitCount + " " + unitName);
		}
		
		if (unitCountParts.size() == 0)
			return "just now";
		
		ret += implodeCommaAnd(unitCountParts);
		ret += " ";
		if (millis <= 0) {
			ret += "ago";
		} else {
			ret += "from now";
		}
		
		return ret;
	}
	
	// String Comparison
	
	public static String getBestCIStart(Collection<String> candidates, String start) {
		String ret = null;
		int best = 0;
		
		start = start.toLowerCase();
		int minlength = start.length();
		for (String candidate : candidates) {
			if (candidate.length() < minlength) {
				continue;
			}
			if (!candidate.toLowerCase().startsWith(start)) {
				continue;
			}
			
			// The closer to zero the better
			int lendiff = candidate.length() - minlength;
			if (lendiff == 0)
				return candidate;
			if (lendiff < best || best == 0) {
				best = lendiff;
				ret = candidate;
			}
		}
		return ret;
	}

	// Tokenization
	
	public static List<String> tokenizeArguments(String str) {
		List<String> ret = new ArrayList<String>();
		StringBuilder token = null;
		boolean escaping = false;
		boolean citing = false;
		
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (token == null) {
				token = new StringBuilder();
			}
			
			if (escaping) {
				escaping = false;
				token.append(c);
			} else if (c == '\\') {
				escaping = true;
			} else if (c == '"') {
				if (citing || token.length() > 0) {
					ret.add(token.toString());
					token = null;
				}
				citing = !citing;
			} else if (citing == false && c == ' ') {
				if (token.length() > 0) {
					ret.add(token.toString());
					token = null;
				}
			} else {
				token.append(c);
			}
		}
		
		if (token != null) {
			ret.add(token.toString());
		}
		
		return ret;
	}
}
