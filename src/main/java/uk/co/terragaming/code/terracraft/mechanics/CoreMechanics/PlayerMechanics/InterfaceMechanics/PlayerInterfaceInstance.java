package uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.InterfaceMechanics;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.TerraCraft;

public class PlayerInterfaceInstance {
	
	private Player player;
	private PlayerInterface iface;
	private Inventory inv;
	private boolean isOpen = false;
	
	public PlayerInterfaceInstance(Player player, PlayerInterface iface) {
		this.player = player;
		this.iface = iface;
	}
	
	public void click(int slot) {
		if (slot >= iface.size)
			return;
		if (!iface.items.containsKey(slot))
			return;
		deRegister();
		Bukkit.getScheduler().runTask(TerraCraft.plugin, new Runnable() {
			
			@Override
			public void run() {
				close();
			}
			
			private void close() {
				isOpen = false;
				player.closeInventory();
			}
		});
		
		iface.items.get(slot).getValue().invoke();
	}
	
	public void create() {
		Inventory inv = Bukkit.createInventory(player, iface.size, iface.title);
		for (int i = 0; i < iface.size; i++) {
			if (!iface.items.containsKey(i)) {
				continue;
			}
			ItemStack is = iface.items.get(i).getKey();
			inv.setItem(i, is);
		}
		this.inv = inv;
	}
	
	public void register() {
		InterfaceMechanics.getInstance().getInterfaceHandler().registerInstance(player.getUniqueId(), this);
	}
	
	public void deRegister() {
		InterfaceMechanics.getInstance().getInterfaceHandler().removeInstance(player.getUniqueId(), this);
	}
	
	public void open() {
		isOpen = true;
		player.openInventory(inv);
	}
	
	public void close() {
		isOpen = false;
		player.closeInventory();
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	public Inventory getInv() {
		return inv;
	}
	
	public PlayerInterface getIFace() {
		return iface;
	}
}
