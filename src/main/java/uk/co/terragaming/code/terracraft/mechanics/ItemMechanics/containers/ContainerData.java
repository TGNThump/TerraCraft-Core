package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers;

import java.sql.SQLException;

import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcItemContainers")
public class ContainerData {
	
	public static Dao<ContainerData, Integer> dao = DatabaseMechanics.getInstance().getDao(ContainerData.class);
	
	@DatabaseField(generatedId = true, canBeNull = false)
	private Integer id;
	
	@DatabaseField(canBeNull = true)
	private Integer size;
	
	@DatabaseField(canBeNull = false)
	private String type;
	
	@ForeignCollectionField(eager = true, columnName = "containerId", foreignFieldName = "containerData")
	private ForeignCollection<Item> items;
	
	@DatabaseField(canBeNull = true)
	private String data;
	
	public void refresh() {
		try {
			dao.refresh(this);
			items.refreshCollection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			dao.update(this);
			items.updateAll();
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
	
	public Integer getSize() {
		return size;
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public ForeignCollection<Item> getItems() {
		return items;
	}
	
	public void setItems(ForeignCollection<Item> items) {
		this.items = items;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
}
