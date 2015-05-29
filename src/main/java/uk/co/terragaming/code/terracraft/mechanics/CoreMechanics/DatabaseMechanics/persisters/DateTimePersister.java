package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.DatabaseMechanics.persisters;

import org.joda.time.DateTime;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.DateType;

public class DateTimePersister extends DateType {
	
	private static final DateTimePersister singleton = new DateTimePersister();
	
	private DateTimePersister() {
		super(SqlType.DATE, new Class<?>[] { DateTime.class });
	}
	
	public static DateTimePersister getSingleton() {
		return singleton;
	}
	
	@Override
	public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
		DateTime dateTime = (DateTime) javaObject;
		if (dateTime == null)
			return null;
		return dateTime.toDate();
	}
	
	@Override
	public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
		return new DateTime(sqlArg);
	}
	
}
