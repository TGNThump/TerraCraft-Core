package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties;


public abstract class Property<P> {
	
	private String key;
	
	public abstract P get();
	public abstract void set(P value);
	
	public abstract Object encode();
	public abstract void decode(Object value);
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
