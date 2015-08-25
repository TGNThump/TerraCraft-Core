package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics;

import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "tcItemComponents")
public class ItemComponentData {
	
	public static Dao<ItemComponentData, Integer> dao = DatabaseMechanics.getInstance().getDao(ItemComponentData.class);
	
	@DatabaseField(generatedId = true, canBeNull = false)
	private Integer id;

	@DatabaseField(canBeNull = false, columnName = "itemId", foreign = true)
	private Item item;
	
	@DatabaseField(canBeNull = false)
	private String type;
	
	@DatabaseField(canBeNull = false)
	private String data;

	public void refresh() {
		try {
			dao.refresh(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			dao.update(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Item getItem(){
		return item;
	}
	
	public void setItem(Item item){
		this.item = item;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
}
