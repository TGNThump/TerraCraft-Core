package uk.co.terragaming.code.terracraft.enums;

public enum PermissionType {
	VIEW(1),
	MODIFY(2),
	CREATE(3),
	DELETE(4);
	
	private final int level;
	
	PermissionType(int level){
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
