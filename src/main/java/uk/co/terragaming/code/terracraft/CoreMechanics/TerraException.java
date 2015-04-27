package uk.co.terragaming.code.terracraft.CoreMechanics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import uk.co.terragaming.code.terracraft.utils.Txt;

public class TerraException extends Exception {

	private static final long serialVersionUID = 1L;
	
	protected List<String> messages = new ArrayList<String>();
	
	public TerraException() {}
	public TerraException(String message) { addMsg(message); }
	
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
	
	public TerraException setMsg(String msg){
		return this.setMessage(Txt.parse(msg));
	}
	
	public TerraException setMsg(String msg, Object... args){
		return this.setMessage(Txt.parse(msg, args));
	}
	
	public TerraException addMessage(String message){
		this.getMessages().add(message);
		return this;
	}
	
	public TerraException addMsg(String msg){
		return this.addMessage(Txt.parse(msg));
	}
	
	public TerraException addMsg(String msg, Object... args){
		return this.addMessage(Txt.parse(msg, args));
	}
	
	public TerraException setMessages(Collection<String> messages){
		this.messages = new ArrayList<String>(messages);
		return this;
	}
	
	public TerraException setMessages(String... messages) {
		return this.setMessages(Arrays.asList(messages));
	}
	
	public TerraException setMsgs(Collection<String> msgs){
		return this.setMessages(Txt.parse(msgs));
	}
	
	public TerraException setMsgs(String... msgs){
		return this.setMsgs(Arrays.asList(msgs));
	}
	
	public TerraException addMessages(Collection<String> messages){
		this.getMessages().addAll(messages);
		return this;
	}

	public TerraException addMessages(String... messages){
		return this.addMessages(Arrays.asList(messages));
	}
	
	public TerraException addMsgs(Collection<String> messages){
		this.getMessages().addAll(messages);
		return this;
	}
	
	public TerraException addMsgs(String... msgs){
		return this.addMsgs(Arrays.asList(msgs));
	}
}
