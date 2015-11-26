package uk.co.terragaming.TerraCore.Events;

import org.bukkit.event.Cancellable;


public class CancellableTerraEvent extends TerraEvent implements Cancellable{
	
	private boolean cancelled = false;
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
	
}
