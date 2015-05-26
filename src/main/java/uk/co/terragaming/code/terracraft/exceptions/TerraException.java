package uk.co.terragaming.code.terracraft.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import uk.co.terragaming.zoldCode.V1.terracraft.utils.Txt;

public class TerraException extends Exception{

	private static final long serialVersionUID = 128954646934669639L;
	
	protected List<String> messages = new ArrayList<String>();
	
	public TerraException(){}
	public TerraException(String message){ addMessage(message); }
	
	public boolean hasMessages(){ return !this.messages.isEmpty(); }
	public List<String> getMessages(){ return this.messages; }
	
	@Override
	public String getMessage(){
		return Txt.implode(this.getMessages(), "\n");
	}
	
	public TerraException setMessage(String message){
		this.messages = new ArrayList<String>();
		this.messages.add(message);
		return this;
	}
	
	public TerraException setMessage(String message, Object... args){
		return this.setMessage(String.format(message, args));
	}
	
	public TerraException setMessages(Collection<String> messages){
		this.messages = new ArrayList<String>(messages);
		return this;
	}
	
	public TerraException setMessages(String... messages){
		return this.setMessages(Arrays.asList(messages));
	}

	public TerraException addMessage(String message){
		this.getMessages().add(message);
		return this;
	}
	
	public TerraException addMessage(String message, Object... args){
		return this.addMessage(String.format(message, args));
	}
	
	public TerraException addMessages(Collection<String> messages){
		this.getMessages().addAll(messages);
		return this;
	}
	
	public TerraException addMessages(String... messages){
		return this.addMessages(Arrays.asList(messages));
	}
}
