package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories;

import org.apache.commons.lang3.ArrayUtils;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties.Property;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;


public class PropertyFactory {
	
	@SuppressWarnings("unchecked")
	public static <T> Property<T> create(String key, Class<T> type, Object value){
		try {
			String name = PropertyFactory.class.getName();
			String[] parts = name.split("\\.");
			parts = ArrayUtils.remove(parts, parts.length - 1);
			parts = ArrayUtils.remove(parts, parts.length - 1);
			String path = Txt.implode(parts, ".") + ".properties." + type.getSimpleName() + "Property";
		
			Class<? extends Property<?>> c;
			try{
				c = (Class<? extends Property<?>>) Class.forName(path);
			} catch (Exception e){
				TerraLogger.error("No Serializer Found for " + type.getSimpleName());
				return null;
			}
			
			Property<?> property = c.newInstance();
			property.setKey(key);
			property.decode(value);
			return (Property<T>) property;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
