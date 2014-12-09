package uk.co.terragaming.code.terracraft.CoreMechanics.PermissionMechanics;

public enum permLevel {
	OWNER(1),
	RESTRICTED(2),
	EVERYONE(3);
	
	private final int level;
	
	permLevel(int level){
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
