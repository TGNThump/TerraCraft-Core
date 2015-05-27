package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.interfaces;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics.CallBack;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.CallbackMechanics.annotations.Callback;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.InterfaceMechanics.PlayerInterface;
import uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics.FealtyManager;
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
		
		iface.addIcon(IconMenu.getItem(
			new ItemStack(Material.MONSTER_EGG),
			Txt.parse(Lang.get(lang, "characterShiftMenuTrade")),
			Txt.parse(Lang.get(lang, "characterShiftMenuTradeDesc"), targetChar.getName())),
			new CallBack("trade", this)
		);

		boolean targetIsYourPatron = false;
		
		if (yourChar.getPatron() != null)
			if (yourChar.getPatron().equals(targetChar)) targetIsYourPatron = true;
		
		if (!targetIsYourPatron){
			iface.addIcon(IconMenu.getItem(
					new ItemStack(Material.MONSTER_EGG),
					Txt.parse(Lang.get(lang, "characterShiftMenuSwearFealty")),
					Txt.parse(Lang.get(lang, "characterShiftMenuSwearFealtyDesc"), targetChar.getName()) + (yourChar.getPatron() != null ? Txt.parse(Lang.get(lang, "characterShiftMenuSwearFealtyBreak"), yourChar.getPatron().getName()) : "")),
					new CallBack("swearFealty", new FealtyManager(), you, target)
				);
		} else {
			iface.addIcon(IconMenu.getItem(
					new ItemStack(Material.MONSTER_EGG),
					Txt.parse(Lang.get(lang, "characterShiftMenuBreakFealty")),
					Txt.parse(Lang.get(lang, "characterShiftMenuBreakFealtyDesc"), targetChar.getName())),
					new CallBack("breakFealty", new FealtyManager(), you, target)
				);
		}
		
		iface.addIcon(IconMenu.getItem(
			new ItemStack(Material.MONSTER_EGG),
			Txt.parse(Lang.get(lang, "characterShiftMenuAddFriend")),
			Txt.parse(Lang.get(lang, "characterShiftMenuAddFriendDesc"), targetChar.getName())),
			new CallBack("addFriend", this)
		);
		
		iface.addIcon(IconMenu.getItem(
			new ItemStack(Material.MONSTER_EGG),
			Txt.parse(Lang.get(lang, "characterShiftMenuAddToParty")),
			Txt.parse(Lang.get(lang, "characterShiftMenuAddToPartyDesc"), targetChar.getName())),
			new CallBack("addToParty", this)
		);
	
		iface.createInstance(you).open();
	}
	
	// TODO: Trade
	
	@Callback
	public void trade(){
		you.sendMessage("Trading is not yet implemented.");
	}
	
	@Callback
	public void addFriend(){
		you.sendMessage("Add as Friend");
	}
	
	@Callback
	public void addToParty(){
		you.sendMessage("Add To Party");
	}
	
}
