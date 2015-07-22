package uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components;

import uk.co.terragaming.code.terracraft.enums.ItemBindType;
import uk.co.terragaming.code.terracraft.enums.ItemBinding;
import uk.co.terragaming.code.terracraft.mechanics.CoreMechanics.AccountMechanics.Account;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components.annotations.GlobalProperty;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanicsV2.components.annotations.LocalProperty;


public class BindingComponent extends ItemComponent{
	
	@GlobalProperty
	private ItemBindType bindType;
	
	@LocalProperty
	private ItemBinding binding;
	
	@LocalProperty
	private Character charOwner;
	
	@LocalProperty
	private Account accOwner;
	
	
	public BindingComponent(){
		
	}
	
	@Override
	public String render() {
		return "";
	}
}
