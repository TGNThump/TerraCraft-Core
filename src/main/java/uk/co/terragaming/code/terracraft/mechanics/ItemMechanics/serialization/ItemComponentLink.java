package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization;

import java.util.ArrayList;


public abstract class ItemComponentLink {
	
	public abstract Integer getId();
	public abstract Integer getComponentId();
	public abstract ItemComponentData getComponentData();
	public abstract ArrayList<ItemComponentDataEntry> getData();
	
}
