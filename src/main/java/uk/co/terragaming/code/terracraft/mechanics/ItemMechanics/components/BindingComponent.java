package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import uk.co.terragaming.code.terracraft.enums.ItemBindType;
import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.events.item.ItemBindEvent;
import uk.co.terragaming.code.terracraft.events.item.ItemMoveEvent;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.containers.CharacterContainer;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties.Property;

public class BindingComponent extends ItemComponent{
	
	Property<ItemBindType> bindType = get("bindType", ItemBindType.class);
	Property<ItemBinding> binding = get("binding", ItemBinding.class);
	Property<Character> charOwner = get("charOwner", Character.class);
	Property<Account> accOwner = get("accOwner", Account.class);

	public BindingComponent(){
		if (bindType.get() == null) bindType.set(ItemBindType.NONE);
		if (binding.get() == null) binding.set(ItemBinding.NONE);
	}
	
	// Overrides
	
	@Override
	public String render(Integer lvl) {
		if (lvl != 2) return "";
		if (!getBinding().equals(ItemBinding.NONE)){
			return "<silver>" + (getBinding().equals(ItemBinding.ACCOUNT) ? "Heirloom" : "Soulbound");
		}
		return "";
	}
	
	// Event Handlers
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemMove(ItemMoveEvent event){
		if (event.getItem() != this.getItem()) return;
		if (event.isCancelled()) return;
		if (!(event.getTo() instanceof CharacterContainer)) return;
		CharacterContainer to = (CharacterContainer) event.getTo();
		
		switch (getBinding()){
			case ACCOUNT:
				if (getAccOwner() == null) break;
				if (getAccOwner().equals(to.getCharacter().getAccount())) return;
				event.setCancelled(true);
				return;
			case CHARACTER:
				if (getCharOwner() == null) break;
				if (getCharOwner().equals(to.getCharacter())) return;
				event.setCancelled(true);
				return;
			default: break;
		}
		
		if (getBindType().equals(ItemBindType.PICKUP)
				&& getBinding().equals(ItemBinding.NONE))
			bind(to.getCharacter());
	}
	
	// Methods
	
	public void bind(Account account){
		accOwner.set(account);
		setBinding(ItemBinding.ACCOUNT);
		callBindEvent();
	}
	
	public void bind(Character character){
		charOwner.set(character);
		setBinding(ItemBinding.CHARACTER);
		callBindEvent();
	}
	
	private void callBindEvent(){
		ItemBindEvent event = new ItemBindEvent(getItem(), this);
		Bukkit.getPluginManager().callEvent(event);
	}
	
	// Getters and Setters
	
	public ItemBindType getBindType(){
		if (bindType.get() == null) bindType.set(ItemBindType.NONE);
		return bindType.get();
	}
	
	public ItemBinding getBinding(){
		if (binding.get() == null) binding.set(ItemBinding.NONE);
		return binding.get();
	}
	
	public Character getCharOwner(){
		return charOwner.get();
	}
	
	public Account getAccOwner(){
		return accOwner.get();
	}
	
	public void setBindType(ItemBindType bindType){
		this.bindType.set(bindType);
	}
	
	public void setBinding(ItemBinding binding){
		this.binding.set(binding);
	}
	
	public Player getOwner(){
		switch (getBinding()){
			case ACCOUNT:
				return getAccOwner().getPlayer();
			case CHARACTER:
				return getCharOwner().getAccount().getPlayer();
			case NONE:
			default:
				return null;
		}
	}
}
