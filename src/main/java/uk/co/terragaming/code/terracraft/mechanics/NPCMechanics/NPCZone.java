package uk.co.terragaming.code.terracraft.mechanics.NPCMechanics;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "tcNPCZones")
public class NPCZone {
	
	@DatabaseField(generatedId = true, columnName = "id")
	private Integer id;
	
	@DatabaseField(canBeNull = false, columnName = "world")
	private String worldName;
	
	@DatabaseField(canBeNull = false)
	private Integer x;
	
	@DatabaseField(canBeNull = false)
	private Integer y;

	@DatabaseField(canBeNull = false)
	private Integer z;
	
	@DatabaseField(canBeNull = false)
	private Integer radius;

	public Location getLocation(){
		return new Location(Bukkit.getWorld(worldName), x, y, z);
	}
	
	public void setLocation(Location location){
		worldName = location.getWorld().getName();
		x = location.getBlockX();
		y = location.getBlockY();
		z = location.getBlockZ();
	}
	
	public Integer getId() {
		return id;
	}

	
	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getWorldName() {
		return worldName;
	}

	
	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}

	
	public Integer getX() {
		return x;
	}

	
	public void setX(Integer x) {
		this.x = x;
	}

	
	public Integer getY() {
		return y;
	}

	
	public void setY(Integer y) {
		this.y = y;
	}

	
	public Integer getZ() {
		return z;
	}

	
	public void setZ(Integer z) {
		this.z = z;
	}

	
	public Integer getRadius() {
		return radius;
	}

	
	public void setRadius(Integer radius) {
		this.radius = radius;
	}
}
