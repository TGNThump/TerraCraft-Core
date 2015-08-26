package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties;

import uk.co.terragaming.code.terracraft.utils.Assert;


public class IntegerProperty extends Property<Integer>{

	private Integer value;
	
	public IntegerProperty() {}
	
	public IntegerProperty(int value) {
		set(value);
	}

	@Override
	public Integer get() {
		return value;
	}

	@Override
	public void set(Integer value) {
		this.value = value;
	}

	@Override
	public Object encode() {
		if (value == null) return null;
		return value;
	}

	@Override
	public void decode(Object value) {
		this.value = null;
		if (value == null) return;
		Assert.ofType(value, Number.class);
		this.value = value != null ? ((Number)value).intValue() : null;;
	}
	
}
