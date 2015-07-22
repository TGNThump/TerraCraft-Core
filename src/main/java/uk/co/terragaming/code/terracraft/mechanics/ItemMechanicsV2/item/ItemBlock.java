package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.item;

import org.bukkit.Material;

import uk.co.terragaming.code.terracraft.enums.ItemSource;


public class ItemBlock extends Item{

	private Material block;
	
	public ItemBlock() {
		super();
		super.setName(block.toString());
		super.setMaxDurability(-1);
		super.setIcon(block);
		super.setSource(ItemSource.BLOCK);
	}
	
	@Override
	public String getTypeString() {
		return "";
	}

	@Override
	public String getClassString() {
		return "Block";
	}

	public Material getBlock() {
		return block;
	}

	public void setBlock(Material block) {
		this.block = block;
	}
	
}
