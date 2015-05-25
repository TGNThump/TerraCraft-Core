package uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.interfaces;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import uk.co.terragaming.code.terracraft.enums.ChatChannel;
import uk.co.terragaming.code.terracraft.enums.Language;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.ChatMechanics.listeners.ChatEventListener;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
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
	private final Player target;
	
	public CharacterShiftClickInterface(Player you, Player target){
		PlayerInterface iface = new PlayerInterface(Txt.parse("<gold>Interactions"), 1);
		
		this.you = you;
		this.target = target;
		
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

		if (!yourChar.getPatron().equals(targetChar)){
			iface.addIcon(IconMenu.getItem(
					new ItemStack(Material.MONSTER_EGG),
					Txt.parse(Lang.get(lang, "characterShiftMenuSwearFealty")),
					Txt.parse(Lang.get(lang, "characterShiftMenuSwearFealtyDesc"), targetChar.getName()) + (yourChar.getPatron() != null ? Txt.parse(Lang.get(lang, "characterShiftMenuSwearFealtyBreak"), yourChar.getPatron().getName()) : "")),
					new CallBack("swearFealty", this)
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
	public void swearFealty(){
		AccountRegistry registry = AccountMechanics.getInstance().getRegistry();
		
		Account yourAcc = registry.getAccount(you);
		
		Character yourChar = yourAcc.getActiveCharacter();
		Character targetChar = registry.getAccount(target).getActiveCharacter();
		
		yourChar.setPatron(targetChar);

		ChatEventListener.sendMessageToChannel(you, ChatChannel.LOCAL, "");
		ChatEventListener.sendMessageToChannel(you, ChatChannel.LOCAL, "In the name and sight of the Gods,");
		ChatEventListener.sendMessageToChannel(you, ChatChannel.LOCAL, "I, " + yourChar.getName() + ",");
		ChatEventListener.sendMessageToChannel(you, ChatChannel.LOCAL, "from this day, until my last day,");
		ChatEventListener.sendMessageToChannel(you, ChatChannel.LOCAL, "pledge my sword and my soul,");
		ChatEventListener.sendMessageToChannel(you, ChatChannel.LOCAL, "my land and my life,");
		ChatEventListener.sendMessageToChannel(you, ChatChannel.LOCAL, "to the service and defence of");
		ChatEventListener.sendMessageToChannel(you, ChatChannel.LOCAL, targetChar.getName() + ".");
		ChatEventListener.sendMessageToChannel(you, ChatChannel.LOCAL, "So mote it be.");
		ChatEventListener.sendMessageToChannel(you, ChatChannel.LOCAL, "");
		
	
	
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
