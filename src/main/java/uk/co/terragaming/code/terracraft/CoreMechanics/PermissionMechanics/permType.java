package uk.co.terragaming.code.terracraft.CoreMechanics.PermissionMechanics;

public enum permType {
	VIEW(1),
	MODIFY(2),
	CREATE(3),
	DELETE(4);
	
	private final int level;
	
	permType(int level){
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
