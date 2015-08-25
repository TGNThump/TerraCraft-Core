package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories;

import org.apache.commons.lang3.ArrayUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemComponentData;
import uk.co.terragaming.code.terracraft.utils.Assert;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;

@SuppressWarnings("unchecked")
public class ComponentFactory {
	
	public static <T extends ItemComponent> T create(ItemComponentData dao){
		Assert.notNull(dao);

		JSONObject data;
		try {
			if (dao.getData().isEmpty()){
				data = new JSONObject();
			} else if (dao.getData() == null){
				data = new JSONObject();
			} else data = (JSONObject) new JSONParser().parse(dao.getData());
		} catch (ParseException e1) {
			e1.printStackTrace();
			TerraLogger.error("Failed to convert '<white>" + dao.getData() + "<red>'");
			data = new JSONObject();
		}
		
		try {
			String cName = ComponentFactory.class.getName();
			String[] parts = cName.split("\\.");
			parts = ArrayUtils.remove(parts, parts.length - 1);
			parts = ArrayUtils.remove(parts, parts.length - 1);
			String path = Txt.implode(parts, ".") + ".components." + dao.getType();
		
			Class<? extends ItemComponent> c;
			try{
				c = (Class<? extends ItemComponent>) Class.forName(path);
			} catch (Exception e){
				TerraLogger.error("No Component Found for " + dao.getType());
				return null;
			}
			
			T component = (T) c.newInstance();
			component.setData(data);
			component.setItem(dao.getItem());
			component.setDao(dao);
			component.refresh();
			
			return (T) component;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T extends ItemComponent> T create(Class<T> type, Item item){
		Assert.notNull(type);
		Assert.notNull(item);
		
		try {
			T component = (T) type.newInstance();
			
			ItemComponentData dao = new ItemComponentData();
			
			component.setItem(item);
			component.setDao(dao);
			
			dao.setItem(item);
			dao.setData("");
			dao.setType(type.getSimpleName());
			ItemComponentData.dao.create(dao);
			dao.update();
			
			return component;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T extends ItemComponent> T clone(T toClone, Item item){
		Assert.notNull(toClone);
		Assert.notNull(item);
		ItemComponent clone = ComponentFactory.create(toClone.getClass(), item);
		clone.setData(toClone.getData());
		clone.setItem(item);
		return (T) clone;
	}
}
