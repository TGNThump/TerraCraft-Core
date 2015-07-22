package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.commands;


public class StaffItemCommands {
//	
//	@Command({ "item", "i" })
//	@CommandDescription("Staff Item Command Set")
//	@CommandParent("staff")
//	@HelpCommand
//	public void onStaffItemCommand(Player sender, Language language) {
//		if (sender instanceof Player){
//			Account account = AccountRegistry.getAccount((Player) sender);
//			if (account == null) return;
//			if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
//				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
//				return;
//			}
//		}
//		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] " + Lang.get(language, "commandHelpUsage", !(sender instanceof Player)), "staff item help"));
//	}
//	
//	@Command({ "rand", "r" })
//	@CommandDescription("Create random Item")
//	@CommandParent("staff item")
//	public void onStaffRandomItemCreate(Player sender){
//		if (sender instanceof Player){
//			Account account = AccountRegistry.getAccount((Player) sender);
//			if (account == null) return;
//			if (!(Perms.check(account, 1) || (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT) && Perms.check(account, 2)))){
//				sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You do not have permission to access these commands."));
//				return;
//			}
//		}
//		Item item = ItemFactory.randomItem();
//		sender.getInventory().addItem(item.render());
//	}
//	
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
//		ItemRegistry registry = ItemMechanicsV1.getInstance().getItemRegistry();
//		
//		if (registry.hasItem(itemId)) {
//			ItemInstance item = registry.getItem(itemId).createInstance();
//			Account account = AccountRegistry.getAccount(sender);
//			Character character = account.getActiveCharacter();
//			item.setCharacter(character);
//			if (character == null) {
//				if (perm) {
//					ItemMechanicsV1.getInstance().getItemInstanceRegistry().addItemIfAbsent(item);
//					sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Spawned Permanent Item: " + item.getColouredName()));
//				} else {
//					try {
//						ItemMechanicsV1.getInstance().getItemInstanceDao().delete(item);
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Spawned Temporary Item: " + item.getColouredName()));
//				}
//			} else {
//				if (TerraCraft.serverMode.equals(ServerMode.DEVELOPMENT)) {
//					ItemMechanicsV1.getInstance().getItemInstanceRegistry().addItemToCharacter(character, item);
//					sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Spawned Item %s.", item.getColouredName()));
//				} else {
//					sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] You cannot spawn items without being in staff mode."));
//					return;
//				}
//			}
//			
//			sender.getInventory().addItem(item.getItemStack());
//		} else {
//			sender.sendMessage("[" + ChatColor.AQUA + "TerraCraft" + ChatColor.WHITE + "] Unregistered ItemId: " + itemId);
//		}
//	}
//	
////	@Command({ "clearcache", "clearCache" })
////	@CommandDescription("Clear the Item Cache")
////	@CommandParent("staff item")
////	public void onClearItemCache(Player sender) {
////		ItemMechanics.getInstance().getItemDao().clearObjectCache();
////		sender.sendMessage(Txt.parse("[<l>TerraCraft<r>] Cleared Item Cache"));
////	}
}
