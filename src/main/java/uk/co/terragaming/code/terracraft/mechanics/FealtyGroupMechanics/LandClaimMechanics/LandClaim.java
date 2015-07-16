package uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.LandClaimMechanics;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.mechanics.FealtyGroupMechanics.FealtyGroup;


public class LandClaim {
	
	private FealtyGroup fealtyGroup;
	
	private World world;
	
	private int x;
	private int z;
	
	private boolean isContested;
	
	private DateTime creationDate;
	private DateTime lastActiveDate;
	
	public Chunk getChunk(){
		return world.getChunkAt(x, z);
	}
	
	public FealtyGroup getFealtyGroup() {
		return fealtyGroup;
	}
	
	public void setFealtyGroup(FealtyGroup fealtyGroup) {
		this.fealtyGroup = fealtyGroup;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	public boolean isContested() {
		return isContested;
	}
	
	public void setContested(boolean isContested) {
		this.isContested = isContested;
	}
	
	public DateTime getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}
	
	public DateTime getLastActiveDate() {
		return lastActiveDate;
	}
	
	public void setLastActiveDate(DateTime lastActiveDate) {
		this.lastActiveDate = lastActiveDate;
	}
}
