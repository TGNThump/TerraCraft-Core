package uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics;

import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters.DateTimePersister;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcFealtyGroups")
public class FealtyGroup {
	
	@DatabaseField(generatedId = true, columnName = "fealtyGroupId")
	private Integer id;
	
	@DatabaseField(canBeNull = true)
	private String name;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "patronid")
	private Character patron;
	
	@DatabaseField(canBeNull = false, persisterClass = DateTimePersister.class)
	private DateTime createDate;
	
	@ForeignCollectionField(eager = true, foreignFieldName = "group")
	private ForeignCollection<FealtyGroupStance> stances;

	public FealtyGroup(){}
	
	public Integer getId() {
		return id;
	}

	
	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public Character getPatron() {
		return patron;
	}

	
	public void setPatron(Character patron) {
		this.patron = patron;
	}

	
	public ForeignCollection<FealtyGroupStance> getStances() {
		return stances;
	}

	
	public void setStances(ForeignCollection<FealtyGroupStance> stances) {
		this.stances = stances;
	}


	
	public DateTime getCreateDate() {
		return createDate;
	}


	
	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}
}
