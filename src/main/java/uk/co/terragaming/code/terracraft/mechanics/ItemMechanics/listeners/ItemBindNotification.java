package uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import uk.co.terragaming.code.terracraft.events.item.ItemBindEvent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.BindingComponent;
import uk.co.terragaming.code.terracraft.mechanics.ItemMechanics.components.RenderComponent;
import uk.co.terragaming.code.terracraft.utils.reflection.ActionBar;


public class ItemBindNotification implements Listener{
	
	@EventHandler
	public void onItemBindEvent(ItemBindEvent event){
		BindingComponent binding = event.getBinding();
		String name = event.getItem().get(RenderComponent.class).getColouredName();
		ActionBar.sendMessage(binding.getOwner(), "The %s has been bound to your %s.", name, binding.getBinding().toString().toLowerCase());
	}
	
}
