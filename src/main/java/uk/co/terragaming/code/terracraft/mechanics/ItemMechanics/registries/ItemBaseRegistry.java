package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.registries;

import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemBase;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemMechanics;


public class ItemBaseRegistry {
	
	public static ItemBase get(Integer id){
		try {
			return ItemMechanics.getInstance().getItemBaseDao().queryForId(id);
		} catch (SQLException e) {
			return null;
		}
	}
}
