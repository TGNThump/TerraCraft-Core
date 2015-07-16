package uk.co.terragaming.code.terracraft.mechanics.NPCMechanics;

import java.util.concurrent.ConcurrentHashMap;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCDataStore;
import net.citizensnpcs.api.npc.NPCRegistry;

public class NPCStore implements NPCDataStore{

	private ConcurrentHashMap<Integer, NPC> npcs = new ConcurrentHashMap<Integer, NPC>();
	
	@Override
	public void clearData(NPC npc) {
		npcs.remove(npc);
	}

	@Override
	public int createUniqueNPCId(NPCRegistry registry) {
		int i = 1;
		while (true){
			if (!npcs.containsKey(i)) return i; 
			i++;
		}
	}

	@Override
	public void loadInto(NPCRegistry registry) {
		for (NPC npc : npcs.values()){
			npcs.remove(npc);
			registry.createNPC(npc.getEntity().getType(), npc.getUniqueId(), npc.getId(), npc.getName());
		}
	}

	@Override
	public void saveToDisk() {
		
	}

	@Override
	public void saveToDiskImmediate() {
		
	}

	@Override
	public void store(NPC npc) {
		npcs.put(npc.getId(), npc);
	}

	@Override
	public void storeAll(NPCRegistry registry) {
		for (NPC npc : registry){
			store(npc);
		}
	}
	
}
