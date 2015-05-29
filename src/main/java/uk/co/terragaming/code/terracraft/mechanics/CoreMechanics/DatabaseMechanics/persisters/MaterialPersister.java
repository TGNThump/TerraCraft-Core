package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters;

import org.bukkit.Material;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

public class MaterialPersister extends StringType {
	
	private static final MaterialPersister singleton = new MaterialPersister();
	
	private MaterialPersister() {
		super(SqlType.STRING, new Class<?>[] { Material.class });
	}
	
	public static MaterialPersister getSingleton() {
		return singleton;
	}
	
	@Override
	public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
		Material material = (Material) javaObject;
		if (material == null)
			return null;
		return material.name();
	}
	
	@Override
	public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		return Material.getMaterial((String) sqlArg);
	}
	
}
