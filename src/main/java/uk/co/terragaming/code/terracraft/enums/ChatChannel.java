package uk.co.terragaming.code.terracraft.enums;

public enum ChatChannel {
	LOCAL("local"),
	GLOBAL("global"),
	PARTY("party"),
	STAFF("staff"),
	YELL("yell"),
	EMOTE("emote"),
	OOC("ooc");
	
	private final String channel;
	
	private ChatChannel(final String channel) {
		this.channel = channel;
	}
	
	@Override
	public String toString() {
		return channel;
	}
}
