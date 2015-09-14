package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories;

import org.apache.commons.lang3.ArrayUtils;

import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.Container;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.ContainerData;
import uk.co.terragaming.code.terracraft.utils.Assert;
import uk.co.terragaming.code.terracraft.utils.text.Txt;


public class ContainerFactory {
	
	public static <T extends Container> T create(ContainerData d){
		Assert.notNull(d);
		
		try{
			String cName = ContainerFactory.class.getName();
			String[] parts = cName.split("\\.");
			parts = ArrayUtils.remove(parts, parts.length - 1);
			parts = ArrayUtils.remove(parts, parts.length - 1);
			String path = Txt.implode(parts, ".") + ".containers." + d.getType();

			@SuppressWarnings("unchecked")
			Class<T> cl = (Class<T>) Class.forName(path);
			
			T c = (T) cl.newInstance();			
			c.setDao(d);
			c.refresh();

			return c;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T extends Container> T create(Class<T> t, Integer size){
		Assert.notNull(t);
		
		try{
			T c = (T) t.newInstance();
			
			ContainerData dao = new ContainerData();
			dao.setData("");
			dao.setSize(size);
			dao.setType(t.getSimpleName());
			c.setDao(dao);
			
			ContainerData.dao.create(dao);	
			c.refresh();
			
			return c;
			
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Container> T clone(T toClone){
		Assert.notNull(toClone);
		toClone.update();
		Container clone = create(toClone.getClass(), toClone.getSize());
		clone.setData(toClone.getDao().getData());
		for (Integer slot : toClone.getItems().keySet()){
			Item i = toClone.getItems().get(slot);
			Item ic = ItemFactory.clone(clone, i);
			if (ic == null) continue;
			clone.getItems().put(slot, ic);
		}
		clone.update();
		return (T) clone;
	}
}
