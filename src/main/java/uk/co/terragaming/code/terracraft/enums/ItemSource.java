package uk.co.terragaming.code.terracraft.enums;


public enum ItemSource {
	NPCSPAWN("NPC"),
	COMMAND("CMD"),
	CRAFTED("CRAFT"),
	BLOCK("BLOCK");
	
	private final String itemSource;
	
	private ItemSource(final String itemSource){
		this.itemSource = itemSource;
	}
	
	@Override
	public String toString(){
		return itemSource;
	}
}
