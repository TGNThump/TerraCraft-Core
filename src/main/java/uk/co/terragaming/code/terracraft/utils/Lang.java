package uk.co.terragaming.code.terracraft.utils;

import java.util.HashMap;

import uk.co.terragaming.code.terracraft.enums.Language;

public class Lang {
	private static HashMap<String, HashMap<Language, String>> messages = new HashMap<>();

	public static void load() {

		// English
		
		add(Language.ENGLISH, "internalException", "<b>An internal error has occurred.");
		
		add(Language.ENGLISH, "accountAccessRevoked", "<b>Access to your Account has been revoked from this location.");
		add(Language.ENGLISH, "accountNotLinked", "<b>You have not linked this Minecraft Account to a Terra Gaming Network Account.");
		add(Language.ENGLISH, "accountBanGlobal", "<b>Your account has been GLOBALLY banned from all TGN Services.");
		add(Language.ENGLISH, "accountBanPerm", "<b>Your account has been PERMANENTLY banned from TerraCraft.");
		
		add(Language.ENGLISH, "accountTempBan", "<b>You are TEMPORARILY banned from TerraCraft for another ");
		add(Language.ENGLISH, "accountTempBanDays", " Days, ");
		add(Language.ENGLISH, "accountTempBanHours", " Hours, ");
		add(Language.ENGLISH, "accountTempBanMinutes", " Minutes and ");
		add(Language.ENGLISH, "accountTempBanSeconds", " Seconds.");
		
		add(Language.ENGLISH, "accountUploadFailed", "<b>Failed to uplaod account to database.");
		add(Language.ENGLISH, "accountDownloadFailed", "<b>Failed to downlaod account from database.");
		
		add(Language.ENGLISH, "accountServerMode", "<b>TerraCraft %s is currently '%s'."); // serverName, serverMode
		
		add(Language.ENGLISH, "bifrostAllreadyLinked", "<b>Your account is already linked to a Terra Gaming Network Account.");
		add(Language.ENGLISH, "bifrostLinkFail", "<b>We could not link your Mojang Account to your Terra Gaming Network Account.");
		add(Language.ENGLISH, "bifrostLinkSuccess", "<b>Your account has been successfully linked to your Terra Gaming Network Account.");
		
		add(Language.ENGLISH, "commandError", "An error occurred while trying to process the command.");
		add(Language.ENGLISH, "commandRequiresPlayer", "<b>This command must be run as a Player.");
		add(Language.ENGLISH, "commandRequiresConsole", "<b>This command must be run from the Console.");
		add(Language.ENGLISH, "commandIncorrectUsage", "<b>Incorrect Command Usage.");
		add(Language.ENGLISH, "commandUnknown", "Unknown command. Type \"<c>/%s help<r>\" for help."); // commandPath
		
		add(Language.ENGLISH, "commandHelpUsage", "Use <c>/%s<r> for a list of commands."); // commandPath
		add(Language.ENGLISH, "helpCommandTitle", "Help for \"/%s\" command"); // Command Path
		
		add(Language.ENGLISH, "ArgReaderError", "<b>\"<h>%s<b>\"<b> is not a %s."); // arg, argType
		add(Language.ENGLISH, "ArgReaderErrorSelect", "<b>No %s matches \"<h>%s<b>\"."); // argTypeName, arg
		
		add(Language.ENGLISH, "ArgReaderBooleanYes", "yes");
		add(Language.ENGLISH, "ArgReaderBooleanNo", "no");
		
		add(Language.ENGLISH, "ArgReaderBooleanTrue", "true");
		add(Language.ENGLISH, "ArgReaderBooleanFalse", "false");
		
		add(Language.ENGLISH, "ArgReaderIntegerName", "number");
		add(Language.ENGLISH, "ArgReaderStringName", "text");
		
		add(Language.ENGLISH, "characterChangeInvulnerability", "You are invulnerable for 3 seconds.");
		add(Language.ENGLISH, "characterChangeInvulnerabilityExpire", "Your invulnerability has expired.");
		
		add(Language.ENGLISH, "characterShiftMenuTrade", "<gold>Trade");
		add(Language.ENGLISH, "characterShiftMenuTradeDesc", "<l>Click here to Trade with %s."); // characterName
		
		add(Language.ENGLISH, "characterShiftMenuSwearFealty", "<gold>Swear Fealty");
		add(Language.ENGLISH, "characterShiftMenuSwearFealtyDesc", "<l>Click here to Swear Fealty to %s."); // characterName
		add(Language.ENGLISH, "characterShiftMenuSwearFealtyBreak", "<b> This will break your oath with %s."); // characterName
		add(Language.ENGLISH, "characterShiftMenuBreakFealty", "<gold>Break Fealty");
		add(Language.ENGLISH, "characterShiftMenuBreakFealtyDesc", "<l>Click here to break your oath of fealty to %s."); // characterName
		
		add(Language.ENGLISH, "characterSwearFealty", "I swear, on my honour and life, from this day until the end of days, to serve, protect  %s."); // characterName
		
		add(Language.ENGLISH, "characterBreakFealty", "You broke your oath of allegiance to %s."); // characterName
		add(Language.ENGLISH, "characterBreakFealtyToYou", "%s broke thier oath of allegiance to you."); // characterName
		
		add(Language.ENGLISH, "characterShiftMenuAddFriend", "<gold>Add as Friend");
		add(Language.ENGLISH, "characterShiftMenuAddFriendDesc", "<l>Click here to add %s as a Friend."); // characterName
		
		add(Language.ENGLISH, "characterShiftMenuAddToParty", "<gold>Add to Party");
		add(Language.ENGLISH, "characterShiftMenuAddToPartyDesc", "<l>Click here to add %s to your Party."); // characterName
		
		add(Language.ENGLISH, "commandSetLanguage", "Your language has been set to ENGLISH.");
		
		add(Language.ENGLISH, "ChatInvalidChannel", "<b>There is no chat channel with the name %s."); // channelName
		add(Language.ENGLISH, "ChatSetDefaultChannel", "Your default Channel has been set to %s."); // channelName
		
		
		// Norwegian
		
		add(Language.NORWEGIAN, "internalException", "<b>En intern feiltakelse har inntruffet.");
		
		add(Language.NORWEGIAN, "accountAccessRevoked", "<b>Tilgang til din Konto har blitt blokkert fra denne plasseringen.");
		add(Language.NORWEGIAN, "accountNotLinked", "<b>Du har ikke sammenkoblet denne Minecraft Brukeren til en Terra Gaming Network Bruker.");
		add(Language.NORWEGIAN, "accountBanGlobal", "<b>Din bruker har blitt utestengt GLOBALT fra alle TGN tjenester.");
		add(Language.NORWEGIAN, "accountBanPerm", "<b>Din bruker har blitt PERMANENT utestengt fra TerraCraft.");
		
		add(Language.NORWEGIAN, "accountTempBan", "<b>Du er FOR ØYEBLIKKET utestengt fra TerraCraft for ytterligere ");
		add(Language.NORWEGIAN, "accountTempBanDays", " Dager, ");
		add(Language.NORWEGIAN, "accountTempBanHours", " Timer, ");
		add(Language.NORWEGIAN, "accountTempBanMinutes", " Minutter og ");
		add(Language.NORWEGIAN, "accountTempBanSeconds", " Sekunder.");
		
		add(Language.NORWEGIAN, "accountServerMode", "<b>TerraCraft %s er '%s'.");
		
		add(Language.NORWEGIAN, "bifrostAllreadyLinked", "<b>Din konto er allerede sammenkoblet til en Terra Gaming Network Konto.");
		add(Language.NORWEGIAN, "bifrostLinkFail", "<b>Vi kunne ikke sammenkoble din Mojang Konto til din Terra Gaming Network Konto.");
		add(Language.NORWEGIAN, "bifrostLinkSuccess", "<b>Din konto har blitt koblet til din Terra Gaming Network Konto.");
		
		add(Language.NORWEGIAN, "commandError", "En feiltakelse inntraff når vi prøvde å behandle kommandoen.");
		add(Language.NORWEGIAN, "commandRequiresPlayer", "<b>Denne kommandoen må bli kjørt som en Spiller.");
		add(Language.NORWEGIAN, "commandRequiresConsole", "<b>Denne kommandoen må bli kjørt som en Spiller.");
		add(Language.NORWEGIAN, "commandIncorrectUsage", "<b>Denne kommandoen må bli kjørt som en Spiller.");
		add(Language.NORWEGIAN, "commandUnknown", "Ukjent kommando. Skriv \"<c>/%s help<r>\" for hjelp."); // commandPath
		
		add(Language.NORWEGIAN, "commandHelpUsage", "Bruk <c>/%s<r> for en liste av kommandoer."); // commandPath
		
		add(Language.NORWEGIAN, "ArgReaderError", "<b>\"<h>%s<b>\"<b> er ikke en %s."); // arg, argType
		add(Language.NORWEGIAN, "ArgReaderErrorSelect", "<b>Ingen %s ligner \"<h>%s<b>\"."); // argTypeName, arg
		
		add(Language.NORWEGIAN, "ArgReaderBooleanYes", "ja");
		add(Language.NORWEGIAN, "ArgReaderBooleanNo", "nei");
		
		add(Language.NORWEGIAN, "ArgReaderBooleanTrue", "korrekt");
		add(Language.NORWEGIAN, "ArgReaderBooleanFalse", "falskt");
		
		add(Language.NORWEGIAN, "ArgReaderIntegerName", "nummer");
		add(Language.NORWEGIAN, "ArgReaderStringName", "tekst");
		

	}

	public static String get(String key) {
		return get(Language.ENGLISH, key, true, false);
	}

	public static String get(Language lang, String key){
		return get(lang, key, true, false);
	}
	
	public static String get(Language lang, String key, boolean console) {
		return get(lang, key, true, console);
	}

	public static String get(Language lang, String key, boolean parsed, boolean console) {
		HashMap<Language, String> values = messages.get(key);
		
		if (!values.containsKey(lang)){ TerraLogger.error("No translation for '" + key + "' in language '" + lang.toString() + "'."); }
		
		String ret = values.getOrDefault(lang, values.get(Language.ENGLISH));
		if (parsed)
			ret = Txt.parse(ret, console);
		return ret;
	}

	private static void add(Language lang, String key, String msg) {
		if (messages.containsKey(key)) {
			messages.get(key).put(lang, msg);
		} else {
			HashMap<Language, String> values = new HashMap<>();
			values.put(lang, msg);
			messages.put(key, values);
		}
	}
}
