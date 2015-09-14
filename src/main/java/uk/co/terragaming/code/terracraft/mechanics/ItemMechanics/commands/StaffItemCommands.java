package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import uk.co.terragaming.code.terracraft.TerraCraft;
import uk.co.terragaming.code.terracraft.enums.ItemBindType;
import uk.co.terragaming.code.terracraft.enums.ItemRarity;
import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.enums.ServerMode;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.Command;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandDescription;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.CommandParent;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CommandMechanics.annotations.HelpCommand;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics.Perms;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.Item;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.BindingComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.DamageComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.DurabilityComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.RarityComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.RenderComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.factories.ItemBuilder;
import uk.co.terragaming.code.terracraft.utils.Assert;
import uk.co.terragaming.code.terracraft.utils.text.Lang;
import uk.co.terragaming.code.terracraft.utils.text.Txt;

public class StaffItemCommands {
	
	@Command({ "item", "i" })
	@CommandDescription("Staff Item Command Set")
	@CommandParent("staff")
	@HelpCommand
	public void onStaffItemCommand(Player sender, Language language) {
		if (sender instanceof Player){
			Account account = AccountRegistry.getAccount((Player) sender);
			if (account == null) return;
			if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
				return;
			}
		}
		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "commandHelpUsage", !(sender instanceof Player)), "staff item help"));
	}

	@Command({ "create", "c" })
	@CommandDescription("Create an Item")
	@CommandParent("staff item")
	public void onStaffItemCreateCommand(Player sender, Material material, String name, String itemType, String itemClass){
		Account account = AccountRegistry.getAccount(sender);
		if (account == null) return;
		if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
			return;
		}
		
		Character character = account.getActiveCharacter();
		Item i = new ItemBuilder()
			.setName(name)
			.setIcon(material)
			.setContainer(character.getContainer())
			.setType(itemType)
			.setClass(itemClass)
			.build();
		
		if (i == null){
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Item could not be created..."));
			return;
		}
		
		// TODO: Give StaffModed players a container...
		Assert.notNull(character);
		sender.getInventory().addItem(i.as(RenderComponent.class).render());
	}
	
	@Command({ "createtest", "ct" })
	@CommandDescription("Create a test Item")
	@CommandParent("staff item")
	public void onStaffTestItemCreateCommand(Player sender){
		Account account = AccountRegistry.getAccount(sender);
		if (account == null) return;
		if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
			return;
		}
		
		Character character = account.getActiveCharacter();
		
		// TODO: Give StaffModed players a container...
		Assert.notNull(character);
		
		Item i = new ItemBuilder()
			.setName("Excaliber")
			.setIcon(Material.IRON_SWORD)
			.setContainer(character.getContainer())
			.setType("Sword")
			.setClass("Weapon")
			.build();
		
		if (i == null){
			sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Item could not be created..."));
			return;
		}
		
		RarityComponent rarity = i.add(RarityComponent.class);
		BindingComponent binding = i.add(BindingComponent.class);
		DurabilityComponent durability = i.add(DurabilityComponent.class);
		DamageComponent damage = i.add(DamageComponent.class);
		
		rarity.setRarity(ItemRarity.ARTIFACT);
		binding.setBindType(ItemBindType.PICKUP);
		durability.setMaxDurability(100);
		durability.setCurDurability(100);
		damage.setMinDamage(10);
		damage.setMaxDamage(25);

		sender.getInventory().addItem(i.as(RenderComponent.class).render());
	}
	
//	
//	@Command({ "set", "s" })
//	@CommandDescription("Set a Item Property")
//	@CommandParent("staff item")
//	public void onStaffItemSetCommand(Player sender, String key, String value){
//		
//	}
	
	
//	@Command({ "create", "c" })
//	@CommandDescription("Create an Item")
//	@CommandParent("staff item")
//	public void onStaffItemCreateCommand(Player sender, @TagArg boolean perm, Integer itemId) {
//		if (sender instanceof Player){
//			Account account = AccountRegistry.getAccount((Player) sender);
//			if (account == null) return;
//			if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
//				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
//				return;
//			}
//		}
//		
//		ItemBase iBase = ItemBaseRegistry.get(itemId);
//
//		if (iBase != null) {
//			Account account = AccountRegistry.getAccount(sender);
//			Character character = account.getActiveCharacter();
//			ItemInstance item = ItemFactory.create(iBase);
//			
//			if (character == null) {
//				if (perm) {
//					
//					ItemInstanceRegistry.addItemToCharacter(character, item);
//					sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Spawned Item: " + item.getColouredName()));
//				} else {
//					try {
//						ItemMechanics.getInstance().getItemInstanceDao().delete(item);
//						sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Spawned Temporary Item: " + item.getColouredName()));
//					} catch (SQLException e) {
//						e.printStackTrace();
//						return;
//					}
//				}
//			} else {
//				if (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT)) {
//					sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Spawned Item <n>%s<r>.", item.getBase().getName()));
//					ItemInstanceRegistry.addItemToCharacter(character, item);
//				} else {
//					sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You cannot spawn items without being in staff mode."));
//					return;
//				}
//			}
//			
//			sender.getInventory().addItem(item.render());
//		} else {
//			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Unregistered ItemId: " + itemId);
//		}
//	}
}
