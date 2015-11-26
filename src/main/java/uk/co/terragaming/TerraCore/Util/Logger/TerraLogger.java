package uk.co.terragaming.TerraCore.Util.Logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import uk.co.terragaming.TerraCore.Plugin;
import uk.co.terragaming.TerraCore.Util.Text.Text;

@Singleton
public class TerraLogger extends Logger{

	@Inject
	public TerraLogger(Plugin plugin) {
		super(plugin.getClass().getCanonicalName(), null);
		setParent(plugin.getServer().getLogger());
		setLevel(Level.ALL);
	}
	
	public void debug(Object msg) {
		debug("" + msg, "");
	}
	
	public void info(Object msg) {
		info("" + msg, "");
	}
	
	public void warn(String string) {
		warn(string, "");
	}
	
	public void error(String string) {
		error(string, "");
	}
	
	public void info(String string) {
		info(string, "");
	}
	
	public void debug(String string) {
		debug(string, "");
	}
	
	public void info(String msg, Object... args){
		msg = Text.of(true, "[<l>TerraCraft<r>][<l>INFO<r>] " + msg + "<r>", args);
		this.log(Level.INFO, msg);
	}
	
	public void debug(String msg, Object... args){
		msg = Text.of(true, "[<l>TerraCraft<r>][<l>DEBUG<r>] " + msg + "<r>", args);
		this.log(Level.INFO, msg);
	}
	
	public void warn(String msg, Object... args){
		msg = Text.of(true, "[<l>TerraCraft<r>][<l>WARN<r>] " + msg + "<r>", args);
		this.log(Level.WARNING, msg);
	}
	
	public void error(String msg, Object... args){
		msg = Text.of(true, "[<l>TerraCraft<r>][<l>ERROR<r>] " + msg + "<r>", args);
		this.log(Level.SEVERE, msg);
	}
	
	public void blank(){
		System.out.print(" ");
	}
	
}
