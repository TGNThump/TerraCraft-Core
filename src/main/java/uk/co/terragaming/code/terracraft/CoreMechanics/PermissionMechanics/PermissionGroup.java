package uk.co.terragaming.code.terracraft.CoreMechanics.PermissionMechanics;

import java.util.Arrays;
import java.util.HashMap;

public class PermissionGroup {
	private Integer groupId;
	private String groupName;
	
	//PermissionName, PermissionString
	private HashMap<String, String> permissions = new HashMap<String, String>();
	
	public PermissionGroup(int groupId, String groupName) {
		this.groupId = groupId;
		this.groupName = groupName;
	}
	
	public Integer getGroupId() {
		return groupId;
	}
	
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getPermissionString(String permission){
		return permissions.get(permission);
	}
	
	public void setPermissionSetring(String permission, String permissionLevel){
		permissions.put(permission, permissionLevel);
	}
	
	public boolean hasPermission(String permission, permType permType, permLevel permLevel){
		if (!permissions.containsKey(permission)) return false;
		String permString = getPermissionString(permission);
		permString = permString.substring(permLevel.getLevel() - 1, permLevel.getLevel());
		String binPermString = hexToBinary(permString);
		binPermString = binPermString.substring(permType.getLevel() - 1, permType.getLevel());
		return (binPermString.equals("1"));
	}
	
	private String hexToBinary(String hex) {
	    int i = Integer.parseInt(hex, 16);
	    String bin = Integer.toBinaryString(i);
	    int length = 4 - bin.length();
	    char[] padArray = new char[length];
	    Arrays.fill(padArray, '0');
	    String padString = new String(padArray);
	    bin = padString + bin;
	    return bin;
	}
}
