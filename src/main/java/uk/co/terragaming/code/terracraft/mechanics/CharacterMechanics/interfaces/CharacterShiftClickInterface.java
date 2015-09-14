package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.interfaces;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.AccountRegistry;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.PlayerMechanics.InterfaceMechanics.PlayerInterface;
import uk.co.terragaming.code.terracraft.mechanics.FealtyMechanics.FealtyManager;
import uk.co.terragaming.code.terracraft.utils.Callback;
import uk.co.terragaming.code.terracraft.utils.item.IconMenu;
import uk.co.terragaming.code.terracraft.utils.text.Lang;
import uk.co.terragaming.code.terracraft.utils.text.Txt;

public class CharacterShiftClickInterface {
	
	private final Player you;
	
	// private final Player target;
	
	public CharacterShiftClickInterface(Player you, Player target) {
		PlayerInterface iface = new PlayerInterface(Txt.parse("<gold>Interactions"), 1);
		
		this.you = you;
		// this.target = target;

		Character yourChar = AccountRegistry.getAccount(you).getActiveCharacter();
		Character targetChar = AccountRegistry.getAccount(target).getActiveCharacter();
		
		if (yourChar == null || targetChar == null)
			return;
		
		Language lang = AccountRegistry.getAccount(you).getLanguage();
		
		iface.addIcon(IconMenu.getItem(new ItemStack(Material.MONSTER_EGG), Txt.parse(Lang.get(lang, "characterShiftMenuTrade")), Txt.parse(Lang.get(lang, "characterShiftMenuTradeDesc"), targetChar.getName())), Callback.create(this::trade));
		
		boolean targetIsYourPatron = false;
		
		if (yourChar.getPatron() != null)
			if (yourChar.getPatron().equals(targetChar)) {
				targetIsYourPatron = true;
			}
		
		if (!targetIsYourPatron) {
			iface.addIcon(IconMenu.getItem(new ItemStack(Material.MONSTER_EGG), Txt.parse(Lang.get(lang, "characterShiftMenuSwearFealty")), Txt.parse(Lang.get(lang, "characterShiftMenuSwearFealtyDesc"), targetChar.getName()) + (yourChar.getPatron() != null ? Txt.parse(Lang.get(lang, "characterShiftMenuSwearFealtyBreak"), yourChar.getPatron().getName()) : "")),Callback.create(FealtyManager::swearFealty, you, target));
		} else {
			iface.addIcon(IconMenu.getItem(new ItemStack(Material.MONSTER_EGG), Txt.parse(Lang.get(lang, "characterShiftMenuBreakFealty")), Txt.parse(Lang.get(lang, "characterShiftMenuBreakFealtyDesc"), targetChar.getName())), Callback.create(FealtyManager::breakFealty, you, target));
		}
		
		iface.addIcon(IconMenu.getItem(new ItemStack(Material.MONSTER_EGG), Txt.parse(Lang.get(lang, "characterShiftMenuAddFriend")), Txt.parse(Lang.get(lang, "characterShiftMenuAddFriendDesc"), targetChar.getName())), Callback.create(this::addFriend));
		
		iface.addIcon(IconMenu.getItem(new ItemStack(Material.MONSTER_EGG), Txt.parse(Lang.get(lang, "characterShiftMenuAddToParty")), Txt.parse(Lang.get(lang, "characterShiftMenuAddToPartyDesc"), targetChar.getName())), Callback.create(this::addToParty));
		
		iface.createInstance(you).open();
	}
	
	// TODO: Trade
	
	public void trade() {
		you.sendMessage("Trading is not yet implemented.");
	}
	
	public void addFriend() {
		you.sendMessage("Add as Friend");
	}
	
	public void addToParty() {
		you.sendMessage("Add To Party");
	}
	
}
