package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.interfaces;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics.CallBack;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics.annotations.Callback;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PermissionMechanics.GroupRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.PlayerEffects;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.EffectMechanics.VanishEffect;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.InterfaceMechanics.PlayerInterface;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.NickMechanics.NickRegistry;
import uk.co.terragaming.code.terracraft.utils.CustomItem;
import uk.co.terragaming.code.terracraft.utils.IconMenu;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class CharacterSelectInterface {
	
	private final Player player;
	
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
		
		iface.addIcon(0, 8, IconMenu.getItem(new ItemStack(Material.BOOK_AND_QUILL), ChatColor.GOLD + "New Character", ChatColor.DARK_AQUA + "Click here to create a new RP Character"), new CallBack("createNewCharacter", this));
		iface.addIcon(0, 7, IconMenu.getItem(new ItemStack(Material.IRON_BARDING), ChatColor.GOLD + "Leave Server", ChatColor.DARK_AQUA + "Click here to leave TerraCraft."), new CallBack("quitGame", this));
		
		if (GroupRegistry.isInGroup(account, GroupRegistry.getGroup(1))){
			iface.addIcon(0, 6, IconMenu.getItem(new ItemStack(Material.GOLD_HELMET), ChatColor.GOLD + "Enter Staff Mode", ChatColor.DARK_AQUA + "Click here to enter the game in Staff Mode."), new CallBack("staffModeActivate", this));
		}
		
		for (Character character : account.getCharacters()) {
			CustomItem item = new CustomItem(Material.SKULL_ITEM);
			
			item.setName(ChatColor.GOLD + character.getName());
			item.addLore(ChatColor.DARK_AQUA + "Click here to become " + character.getName() + ".");
			
			iface.addIcon(item.getItemStack(), new CallBack("selectCharacter", this, character.getId()));
		}
		
		iface.closable = false;
		preMenuOpen(player);
		iface.createInstance(player).open();
	}
	
	@Callback
	public void selectCharacter(int charId) {
		Account account = AccountRegistry.getAccount(player);
		
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("characterId", charId);
		conditions.put("accountId", account.getId());
		
		try {
			List<Character> chars = CharacterMechanics.getInstance().getCharacterDao().queryForFieldValues(conditions);
			
			for (Character character : chars) {
				CharacterManager.setActiveCharacter(account, character);
				break;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			player.kickPlayer(Txt.parse(Lang.get(account.getLanguage(), "internalException")));
			return;
		}
	}
	
	@Callback
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
	
	@Callback
	public void staffModeActivate() {
		Account account = AccountRegistry.getAccount(player);
		account.setActiveCharacter(null);
		player.setGameMode(GameMode.CREATIVE);
		player.setAllowFlight(true);
		PlayerEffects.addEffect(player, PlayerEffect.STAFFMODE);
		if (!VanishEffect.canSeeVanished.contains(player.getUniqueId())) {
			VanishEffect.canSeeVanished.add(player.getUniqueId());
			VanishEffect.vanishRefresh(player);
		}
		
		Channel staffChannel = ChannelManager.getChannel("staff");
		
		staffChannel.add(player);
		account.setActiveChannel(staffChannel);
		
	}
	
	@Callback
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
		
		player.setHealth(20d);
		player.setFoodLevel(200);
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[4]);
		player.setGameMode(GameMode.SURVIVAL);
		player.setCanPickupItems(false);
		player.setNoDamageTicks(0);
		player.setLevel(0);
		player.setExp(0f);
		player.setAllowFlight(false);
		PlayerEffects.clearEffects(player);
		PlayerEffects.addEffect(player, PlayerEffect.INVULNERABLE);
		PlayerEffects.addEffect(player, PlayerEffect.INVISIBLE);
		NickRegistry.removeNick(player.getUniqueId());
	}
	
}
