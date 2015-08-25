package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import uk.co.terragaming.code.terracraft.enums.ItemBindType;
import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.mechanics.CharacterMechanics.Character;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.ItemComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.properties.Property;

public class BindingComponent extends ItemComponent{
	
	Property<ItemBindType> bindType = get("bindType", ItemBindType.class);
	Property<ItemBinding> binding = get("binding", ItemBinding.class);
	Property<Character> charOwner = get("charOwner", Character.class);
	Property<Account> accOwner = get("accOwner", Account.class);

	@Override
	public String render(Integer lvl) {
		if (lvl != 2) return "";
		if (!getBinding().equals(ItemBinding.NONE)){
			return "<silver>" + (getBinding().equals(ItemBinding.ACCOUNT) ? "Heirloom" : "Soulbound");
		}
		return "";
	}
	
	public ItemBindType getBindType(){
		return bindType.get();
	}
	
	public ItemBinding getBinding(){
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
}
