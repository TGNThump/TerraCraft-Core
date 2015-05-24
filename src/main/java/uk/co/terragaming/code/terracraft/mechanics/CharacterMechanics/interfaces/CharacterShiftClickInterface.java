package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.interfaces;


import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics.CallBack;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics.annotations.Callback;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.InterfaceMechanics.PlayerInterface;
import uk.co.terragaming.code.terracraft.utils.IconMenu;
import uk.co.terragaming.code.terracraft.utils.Lang;
import uk.co.terragaming.code.terracraft.utils.Txt;

public class CharacterShiftClickInterface {

	private final Player you;
	//private final Player target;
	
	public CharacterShiftClickInterface(Player you, Player target){
		PlayerInterface iface = new PlayerInterface(Txt.parse("<gold>Interactions"), 1);
		
		this.you = you;
		//this.target = target;
		
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		
		Character yourChar = registry.getAccount(you).getActiveCharacter();
		Character targetChar = registry.getAccount(target).getActiveCharacter();
		
		if (yourChar == null || targetChar == null) return;
		
		Language lang = registry.getAccount(you).getLanguage();

		ItemStack is = new ItemStack(Material.MONSTER_EGG);
		is.setData(new SpawnEgg(EntityType.RABBIT));
	
		iface.addIcon(IconMenu.getItem(
				is,
				Txt.parse(Lang.get(lang, "characterShiftMenuTrade")),
				Txt.parse(Lang.get(lang, "characterShiftMenuTradeDesc"), targetChar.getName())),
				new CallBack("trade", this));
		
		is = new ItemStack(Material.MONSTER_EGG);
		is.setData(new SpawnEgg(EntityType.WOLF));
		
		iface.addIcon(IconMenu.getItem(
			is,
			Txt.parse(Lang.get(lang, "characterShiftMenuSwearFealty")),
			Txt.parse(Lang.get(lang, "characterShiftMenuSwearFealtyDesc"), targetChar.getName())),
			new CallBack("swearFealty", this));
		
		is = new ItemStack(Material.MONSTER_EGG);
		is.setData(new SpawnEgg(EntityType.CREEPER));
		
		iface.addIcon(IconMenu.getItem(
				is,
				Txt.parse(Lang.get(lang, "characterShiftMenuAddFriend")),
				Txt.parse(Lang.get(lang, "characterShiftMenuAddFriendDesc"), targetChar.getName())),
				new CallBack("addFriend", this));
	
		iface.createInstance(you).open();
	}
	
	@Callback
	public void trade(){
		you.sendMessage("Trade");
	}
	
	@Callback
	public void swearFealty(){
		you.sendMessage("Swear Fealty");
	}
	
	@Callback
	public void addFriend(){
		you.sendMessage("Add Friend");
	}
	
}
