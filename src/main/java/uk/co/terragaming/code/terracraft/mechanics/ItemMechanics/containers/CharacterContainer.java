package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;

public class CharacterContainer extends Container{
	
	private Character character;

	@SuppressWarnings("unchecked")
	@Override
	public void update(){
		if (character != null){
			data.put("name", character.getName());
			data.put("cId", character.getId());
			data.put("aId", character.getAccount().getId());
		}
		super.update();
	}
	
//	@Override
//	public void refresh(){
//		super.refresh();
//		try {
//			Integer id = (Integer) data.get("cId");
//			if (id == null) return;
//			character = CharacterMechanics.getInstance().getCharacterDao().queryForId(id);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	
	public Character getCharacter() {
		return character;
	}

	
	public void setCharacter(Character character) {
		this.character = character;
	}
	
}
