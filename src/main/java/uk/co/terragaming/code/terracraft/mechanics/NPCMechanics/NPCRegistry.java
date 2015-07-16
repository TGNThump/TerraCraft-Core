package uk.co.terragaming.code.terracraft.mechanics.NPCMechanics;

import org.bukkit.entity.EntityType;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;


public class NPCRegistry {
	
	private NPCStore store;
	private net.citizensnpcs.api.npc.NPCRegistry registry;
	
	public NPCRegistry(){
		store = new NPCStore();
		registry = CitizensAPI.createNamedNPCRegistry("TerraCraft", store);
	}
	
	public NPC createNPC(String name){
		return createNPC(name, name);
	}
	
	public NPC createNPC(String name, String skin){
		return registry.createNPC(EntityType.PLAYER, name);
	}
	
	public void destoryNPC(NPC npc){
		if (npc.isSpawned()) npc.despawn();
		npc.destroy();
	}
	
	public void destroyAll(){
		for (NPC npc : registry){
			if (npc.isSpawned()) npc.despawn();
		}
		registry.deregisterAll();
	}
}
