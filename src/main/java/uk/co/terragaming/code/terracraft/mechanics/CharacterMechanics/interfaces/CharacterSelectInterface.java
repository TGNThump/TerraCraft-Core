package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.interfaces;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.joda.time.DateTime;

import uk.co.terragaming.code.terracraft.enums.PlayerEffect;
import uk.co.terragaming.code.terracraft.events.character.CharacterLeaveEvent;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterManager;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.CharacterMechanics;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.ChannelManager;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.channels.Channel;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics.GroupRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.LoadingMode;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.VanishEffect;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.InterfaceMechanics.PlayerInterface;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.InterfaceMechanics.PlayerInterfaceInstance;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NickMechanics.NickRegistry;
import uk.co.terragaming.code.terracraft.utils.Callback;
import uk.co.terragaming.code.terracraft.utils.item.CustomItem;
import uk.co.terragaming.code.terracraft.utils.item.IconMenu;
import uk.co.terragaming.code.terracraft.utils.text.Txt;

public class CharacterSelectInterface {
	
	private final Player player;
	private final PlayerInterfaceInstance ifaceInstance;
	
	public CharacterSelectInterface(Player player) {
		this.player = player;
		Account account = AccountRegistry.getAccount(player);
		
		int i = 2;
		if (GroupRegistry.isInGroup(account, GroupRegistry.getGroup(1))){
			i++;
		}
		i += account.getCharacters().size();
		
		int rows = i / 9;
		rows = rows + 1;
		PlayerInterface iface = new PlayerInterface(Txt.parse("<gold>Your Characters"), rows);
		
		iface.addIcon(0, 8, IconMenu.getItem(new ItemStack(Material.BOOK_AND_QUILL), ChatColor.GOLD + "New Character", ChatColor.DARK_AQUA + "Click here to create a new RP Character"), Callback.create(this::createNewCharacter));
		iface.addIcon(0, 7, IconMenu.getItem(new ItemStack(Material.IRON_BARDING), ChatColor.GOLD + "Leave Server", ChatColor.DARK_AQUA + "Click here to leave TerraCraft."), Callback.create(this::quitGame));
		
		if (GroupRegistry.isInGroup(account, GroupRegistry.getGroup(1))){
			iface.addIcon(0, 6, IconMenu.getItem(new ItemStack(Material.GOLD_HELMET), ChatColor.GOLD + "Enter Staff Mode", ChatColor.DARK_AQUA + "Click here to enter the game in Staff Mode."), Callback.create(this::staffModeActivate));
		}
		
		for (Character character : account.getCharacters()) {
			CustomItem item = new CustomItem(Material.SKULL_ITEM);
			
			item.setName(ChatColor.GOLD + character.getName());
			item.addLore(ChatColor.DARK_AQUA + "Click here to become " + character.getName() + ".");
			
			iface.addIcon(item.getItemStack(), Callback.create(this::selectCharacter, character.getId()));
		}
		
		iface.closable = false;
		preMenuOpen(player);
		ifaceInstance = iface.createInstance(player);
		ifaceInstance.open();
	}
	
	public void selectCharacter(Integer charId) {
		ifaceInstance.close();
		
		Account account = AccountRegistry.getAccount(player);
		CharacterManager.setActiveCharacter(account, charId);
	}
	
	public void createNewCharacter() {
		Account account = AccountRegistry.getAccount(player);
		Character character = new Character();
		character.setAccount(account);
		character.setCreateDate(DateTime.now());
		character.setLastLoginDate(DateTime.now());
		character.setLocation(Bukkit.getWorld("TerraCraft_old").getSpawnLocation());
		character.setName(account.getTerraTag());
		
		try {
			CharacterMechanics.getInstance().getCharacterDao().create(character);
			account.getCharacters().refreshCollection();
		} catch (SQLException e) {
			e.printStackTrace();
			new CharacterSelectInterface(player);
		}
		
		selectCharacter(character.getId());
		
		player.sendMessage(Txt.parse("[<l>TerraCraft<r>] You successfully created a new Character, set the characters name by typing <c>/character name <name><r>."));
	}
	
	public void staffModeActivate() {
		Account account = AccountRegistry.getAccount(player);
		account.setActiveCharacter(null);
		player.setGameMode(GameMode.CREATIVE);
		player.setAllowFlight(true);
		PlayerEffects.addEffect(player, PlayerEffect.STAFFMODE);
		PlayerEffects.removeEffect(player, PlayerEffect.NOCHAT);
		PlayerEffects.removeEffect(player, PlayerEffect.NOMOVE);
		if (!VanishEffect.canSeeVanished.contains(player.getUniqueId())) {
			VanishEffect.canSeeVanished.add(player.getUniqueId());
			VanishEffect.vanishRefresh(player);
		}
		
		Channel staffChannel = ChannelManager.getChannel("staff");
		
		staffChannel.add(player);
		account.setActiveChannel(staffChannel);
		
	}
		
	public void quitGame() {
		player.kickPlayer(Txt.parse("<l>Thanks for Playing!"));
	}
	
	public void preMenuOpen(Player player) {		
		Account account = AccountRegistry.getAccount(player);
		if (account != null){
			Character activeCharacter = account.getActiveCharacter();
			if (activeCharacter != null){
				
				CharacterLeaveEvent event = new CharacterLeaveEvent(activeCharacter, player);
				Bukkit.getServer().getPluginManager().callEvent(event);
			}
		}
		
		account.setActiveChannel(null);
		
		LoadingMode.loading.remove(player.getUniqueId());
		
		player.setHealth(20d);
		player.setFoodLevel(200);
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[4]);
		player.setGameMode(GameMode.SPECTATOR);
		player.setCanPickupItems(false);
		player.setNoDamageTicks(0);
		player.setLevel(0);
		player.setExp(0f);
		PlayerEffects.removeEffect(player, PlayerEffect.STAFFMODE);
		PlayerEffects.addEffect(player, PlayerEffect.INVISIBLE);
		PlayerEffects.addEffect(player, PlayerEffect.INVULNERABLE);
		PlayerEffects.addEffect(player, PlayerEffect.NOCHAT);
		PlayerEffects.addEffect(player, PlayerEffect.NOMOVE);
		NickRegistry.removeNick(player.getUniqueId());
	}
	
}
