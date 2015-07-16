package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.item;

import org.bukkit.Material;

import uk.co.terragaming.code.terracraft.enums.ItemSource;


public class ItemBlock extends Item{

	private Material block;
	
	public ItemBlock(Material block) {
		super(ItemSource.BLOCK);
		super.setName(block.toString());
		super.setMaxDurability(-1);
		super.setIcon(block);
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
