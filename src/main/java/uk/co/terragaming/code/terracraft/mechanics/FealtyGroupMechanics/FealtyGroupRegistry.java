package uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics;

import java.util.HashMap;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;


public class FealtyGroupRegistry {
	
	private static FealtyGroupRegistry instance;
	
	private HashMap<Integer, FealtyGroup> fGroupsById;
	private HashMap<Integer, Integer> fGroupsByPatronId;
	
	public void init(){
		instance = this;
		fGroupsById = new HashMap<>();
		fGroupsByPatronId = new HashMap<>();
	}
	
	public static boolean isGroupPatron(Character yourChar) {
		return instance.fGroupsByPatronId.containsKey(yourChar.getId());
	}

	public static FealtyGroup getByPatron(Character yourChar) {
		return getById(instance.fGroupsByPatronId.get(yourChar.getId()));
	}
	
	public static FealtyGroup getById(Integer id){
		return instance.fGroupsById.get(id);
	}

	public static void remove(FealtyGroup group) {
		instance.fGroupsByPatronId.remove(group.getPatron().getId());
		instance.fGroupsById.remove(group.getId());
	}

	public static void add(FealtyGroup group) {
		instance.fGroupsById.put(group.getId(), group);
		instance.fGroupsByPatronId.put(group.getPatron().getId(), group.getId());
	}
}
