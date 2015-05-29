package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics;

import org.bukkit.Location;

import uk.co.terragaming.code.terracraft.TerraCraft;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tcRaces")
public class CharacterRace {
	
	@DatabaseField(generatedId = true, columnName = "raceId")
	private Integer id;
	
	@DatabaseField(canBeNull = false)
	private String name;
	
	@DatabaseField(canBeNull = true)
	private String description;
	
	@DatabaseField(canBeNull = false, useGetSet = true)
	private String spawnWorld;
	
	@DatabaseField(canBeNull = false, useGetSet = true)
	private Integer spawnX;
	
	@DatabaseField(canBeNull = false, useGetSet = true)
	private Integer spawnY;
	
	@DatabaseField(canBeNull = false, useGetSet = true)
	private Integer spawnZ;
	
	@DatabaseField(canBeNull = false, useGetSet = true)
	private Integer spawnPitch;
	
	@DatabaseField(canBeNull = false, useGetSet = true)
	private Integer spawnYaw;
	
	// Non database Fields
	
	private Location spawnLocation = TerraCraft.server.getWorlds().get(0).getSpawnLocation();
	
	// Getters
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getSpawnWorld() {
		return spawnWorld;
	}
	
	public Integer getSpawnX() {
		return spawnX;
	}
	
	public Integer getSpawnY() {
		return spawnY;
	}
	
	public Integer getSpawnZ() {
		return spawnZ;
	}
	
	public Integer getSpawnPitch() {
		return spawnPitch;
	}
	
	public Integer getSpawnYaw() {
		return spawnYaw;
	}
	
	public Location getSpawnLocation() {
		return spawnLocation.clone();
	}
	
	// Setters
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSpawnWorld(String world) {
		spawnWorld = world;
		spawnLocation.setWorld(TerraCraft.server.getWorld(world));
	}
	
	public void setSpawnX(Integer x) {
		spawnX = x;
		spawnLocation.setX(x);
	}
	
	public void setSpawnY(Integer y) {
		spawnY = y;
		spawnLocation.setY(y);
	}
	
	public void setSpawnZ(Integer z) {
		spawnZ = z;
		spawnLocation.setZ(z);
	}
	
	public void setSpawnPitch(Integer pitch) {
		spawnPitch = pitch;
		spawnLocation.setPitch(pitch);
	}
	
	public void setSpawnYaw(Integer yaw) {
		spawnYaw = yaw;
		spawnLocation.setYaw(yaw);
	}
	
	public void setSpawnLocation(Location loc) {
		spawnLocation = loc;
		spawnWorld = loc.getWorld().getName();
		spawnX = loc.getBlockX();
		spawnY = loc.getBlockY();
		spawnZ = loc.getBlockZ();
		spawnPitch = Math.round(loc.getPitch());
		spawnYaw = Math.round(loc.getYaw());
	}
	
	// Init
	
	public CharacterRace() {}
	
	// Override Methods
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass())
			return false;
		return id.equals(((CharacterClass) other).getId());
	}
	
	@Override
	public String toString() {
		return "CharacterRace[ID: " + getId() + ", Name: " + getName() + "]";
	}
}
