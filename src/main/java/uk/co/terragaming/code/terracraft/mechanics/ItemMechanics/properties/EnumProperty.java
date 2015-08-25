package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties;

import uk.co.terragaming.code.terracraft.utils.Assert;

@SuppressWarnings("unchecked")
public abstract class EnumProperty<T extends Enum<T>> extends Property<T>{

	private Enum<?> value;
	
	protected abstract Class<? extends Enum<?>> getEnumClass();
	
	@Override
	public T get() {
		return (T) value;
	}

	@Override
	public void set(T value) {
		this.value = (Enum<?>) value;
	}

	@Override
	public Object encode() {
		if (value == null) return null;
		return value.name();
	}
	
	@Override
	public void decode(Object value) {
		this.value = null;
		if (value == null) return;
		Assert.ofType(value, String.class);
		this.value = Enum.valueOf((Class<T>) getEnumClass(), (String) value);
	}
	
}
