package uk.co.terragaming.code.terracraft.exceptions;

public class CommandException extends TerraException {
	
	private static final long serialVersionUID = -2925824542685571275L;
	
	public CommandException() {}
	
	public CommandException(String messages) {
		super(messages);
	}
}
