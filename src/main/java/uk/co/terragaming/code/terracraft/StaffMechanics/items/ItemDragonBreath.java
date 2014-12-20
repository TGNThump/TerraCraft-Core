package uk.co.terragaming.code.terracraft.StaffMechanics.items;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.CoreMechanics.ItemMechanics.CustomItem;


public class ItemDragonBreath extends CustomItem {
	public ItemStack item;
	
	public ItemDragonBreath(){
		super(Material.BLAZE_POWDER);
		super.setName(ChatColor.AQUA + "Dragon Breath");
		super.addLore(ChatColor.DARK_GRAY + "Primary: Fireball.");
		super.addLore(ChatColor.DARK_GRAY + "Secondary: Breath Fire");
		super.addLore(ChatColor.GRAY + "Untradable");
		super.addEnchantment(Enchantment.DAMAGE_ALL, 10);
		
		this.item = super.getItemStack();
	}
	
	
	public static HashMap<FallingBlock, BukkitTask> taskIdMap = new HashMap<FallingBlock, BukkitTask>();
	
	//Event Handlers
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
		if (event.getItem() == null){
			
		} else {
			if (event.getItem().hasItemMeta()){
				if (event.getItem().getItemMeta().hasDisplayName()){
					if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Dragon Breath")){
						if (event.getAction().equals(Action.RIGHT_CLICK_AIR)){
							Player player = event.getPlayer();
							Location loc = player.getEyeLocation();
							
							Vector direction = loc.getDirection().normalize();
							direction.multiply(-8);
							
							direction.setY(0);
							
							
							loc.subtract(direction);
							
							player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1, 1);
							Vector vel = loc.getDirection().multiply(2.5F);
							
							@SuppressWarnings("deprecation")
							final FallingBlock fireball = player.getWorld().spawnFallingBlock(loc, Material.FIRE, (byte) 0x0);
							fireball.setVelocity(vel);
							
							fireball.setMetadata("DragonFire", new FixedMetadataValue(TerraCraft.Plugin(), true));
							
							taskIdMap.put(fireball, TerraCraft.Plugin().getServer().getScheduler().runTaskTimerAsynchronously(TerraCraft.Plugin(), new Runnable(){

								@Override
								public void run() {
									try{
										if (fireball.isDead()){
											taskIdMap.get(fireball).cancel();
										} else {
											fireball.getLocation().getWorld().playEffect(fireball.getLocation(), Effect.MOBSPAWNER_FLAMES, 0, 100);
											fireball.getLocation().getWorld().playEffect(fireball.getLocation(), Effect.MOBSPAWNER_FLAMES, 0, 100);
											fireball.getLocation().getWorld().playEffect(fireball.getLocation(), Effect.BLAZE_SHOOT, 0, 100);
											fireball.getLocation().getWorld().playSound(fireball.getLocation(), Sound.FIRE, 10, 2);
											
											int x = fireball.getLocation().getBlockX();
											int y = fireball.getLocation().getBlockY();
											int z = fireball.getLocation().getBlockZ();
											
											
											for (x = -(2); x <= 2; x ++)
											{
											    for (y = -(2); y <= 2; y ++)
											    {
											        for (z = -(2); z <= 2; z ++)
											        {
											        	
											            Block b = fireball.getWorld().getBlockAt(x, y, z);
											            if (b.getType().equals(Material.ICE)){
											            	b.setType(Material.WATER);
											            }
											        }
											    }
											}
											
										}
									} catch(Exception e) {
										taskIdMap.get(fireball).cancel();
									}
								}
							}, 0L, 1));

							
							
							
							
						} else if (event.getAction().equals(Action.LEFT_CLICK_AIR ) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
							Player player = event.getPlayer();
							Location loc = player.getEyeLocation();
							
							Vector direction = loc.getDirection().normalize();
							direction.multiply(-8);
							
							direction.setY(0);
							
							
	 						loc.subtract(direction);
							
							player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1, 1);
							Vector vel = loc.getDirection().multiply(2.5F);
							
							
							final Projectile fireball = player.launchProjectile(LargeFireball.class);
							fireball.setVelocity(vel);
							
							fireball.setFireTicks(0);
							
							fireball.setMetadata("DragonFireBall", new FixedMetadataValue(TerraCraft.Plugin(), true));
							
						}
					}
				}
			}
		}
	}
	
	
	@EventHandler
	public void onHitFireBreath(EntityChangeBlockEvent event){
		if (event.getEntity().hasMetadata("DragonFire")){
			World world = event.getBlock().getWorld();
			Location loc = event.getBlock().getLocation();
			
			float power = (float)1 + (int)(Math.random() * ((4 - 1) + 1)); 
						
			world.createExplosion(loc.getX(), loc.getY(), loc.getZ(), power , true, false);
			world.createExplosion(loc.getX(), loc.getY(), loc.getZ(), 6 , false, false);
		}
	}
	
	@EventHandler
	public void ItemSpawn(ItemSpawnEvent event){
		if (event.getEntity().getItemStack().getType().equals(Material.FIRE)){
			event.getEntity().remove();
		}
	}
	
	@EventHandler
	public void onHitFireBall(ProjectileHitEvent event){
		
		if (event.getEntity().hasMetadata("DragonFireBall")) {
			
			Location loc = event.getEntity().getLocation();
			World world = loc.getWorld();
			
			float power = (float)3 + (int)(Math.random() * ((8 - 3) + 1)); 
			
			world.createExplosion(loc.getX(), loc.getY(), loc.getZ(), power , false, true);
		}
	}
}
