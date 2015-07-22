package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization;

import org.apache.commons.lang3.ArrayUtils;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.serialization.serializers.Serializer;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;
import uk.co.terragaming.code.terracraft.utils.Txt;


public abstract class ItemComponentDataEntry {
	
	public abstract Integer getId();
	public abstract ItemComponentLink getLink();
	public abstract String getFieldName();
	public abstract String getFieldType();
	
	protected abstract String getStrFieldValue();
	protected abstract void setStrFieldValue(String value);
	
	@SuppressWarnings("unchecked")
	public Object getFieldValue(){
		if (getStrFieldValue() == null) return null; 
		try {
			String fieldType = getFieldType();
			
			String name = getClass().getName();
			String[] parts = name.split("\\.");
			parts = ArrayUtils.remove(parts, parts.length - 1);
			String path = Txt.implode(parts, ".") + ".serializers." + fieldType + "Serializer";
		
			Class<? extends Serializer<?>> c;
			try{
				c = (Class<? extends Serializer<?>>) Class.forName(path);
			} catch (Exception e){
				TerraLogger.error("No Serializer Found for " + fieldType);
				return null;
			}
			
			
			return c.newInstance().StringToObject(getStrFieldValue());
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setFieldValue(Object value){
		if(value == null){
			setStrFieldValue(null);
			return;
		}
		
		try {
			String fieldType = getFieldType();
			
			String name = getClass().getName();
			String[] parts = name.split("\\.");
			parts = ArrayUtils.remove(parts, parts.length - 1);
			String path = Txt.implode(parts, ".") + ".serializers." + fieldType + "Serializer";
		
		
			Class<? extends Serializer<?>> c;
			try{
				c = (Class<? extends Serializer<?>>) Class.forName(path);
			} catch (Exception e){
				TerraLogger.error("No Serializer Found for " + fieldType);
				setStrFieldValue(null);
				return;
			}
			
			setStrFieldValue(c.newInstance().ObjectToString(value));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
