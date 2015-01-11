package uk.co.terragaming.code.terracraft.enums;

public enum PermissionLevel {
	OWNER(1),
	RESTRICTED(2),
	EVERYONE(3);
	
	private final int level;
	
	PermissionLevel(int level){
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
