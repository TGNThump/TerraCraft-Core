package uk.co.terragaming.code.terracraft.CoreMechanics.PermissionMechanics;

public class PermissionGroupKey {

    private final int id;
    private final String name;

    public PermissionGroupKey(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Integer getId(){
    	return id;
    }
    
    public String getName(){
    	return name;
    }

    @Override
    public boolean equals(Object o) {
    	if (this == o) return true;
        if (!(o instanceof PermissionGroupKey)) return false;
        PermissionGroupKey key = (PermissionGroupKey) o;
        return id == key.id && name == key.name;
    }
    
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

}