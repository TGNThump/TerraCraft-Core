package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components;

import uk.co.terragaming.code.terracraft.enums.ItemBindType;
import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components.annotations.GlobalProperty;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components.annotations.LocalProperty;


public class BindingComponent extends ItemComponent{
	
	@GlobalProperty
	public ItemBindType bindType;
	
	@LocalProperty
	public ItemBinding binding;
	
	@LocalProperty
	public Character charOwner;
	
	@LocalProperty
	public Account accOwner;
	
	
	public BindingComponent(){
		
	}
	
	@Override
	public String render() {
		if (!getBinding().equals(ItemBinding.NONE)){
			return "<silver>" + (getBinding().equals(ItemBinding.ACCOUNT) ? "Heirloom" : "Soulbound");
		}
		return "";
	}
	
	public ItemBinding getBinding(){
		if (binding == null) binding = ItemBinding.NONE;
		return binding;
	}
}
