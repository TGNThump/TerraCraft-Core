package uk.co.terragaming.code.terracraft;

public enum ServerMode {
	LOADING,		// NOBODY
	LOCKED, 		// TGN Staff, Developers, Moderators Only
	DEVELOPMENT,	// TGN Staff & Developers Only
	CLOSED_BETA,	// TGN Beta Members Only
	OPEN_BETA,		// All Project Bifrost Registered Players
	OPEN			// All Project Bifrost Registered Players
;

	public static ServerMode fromString(String string) {
		switch(string.toUpperCase()){
		case "LOCKED":
			return ServerMode.LOCKED;
		case "DEVELOPMENT":
			return ServerMode.DEVELOPMENT;
		case "CLOSED_BETA":
			return ServerMode.CLOSED_BETA;
		case "OPEN_BETA":
			return ServerMode.OPEN_BETA;
		case "OPEN":
			return ServerMode.OPEN;
		}
		return ServerMode.LOCKED;
	}
}