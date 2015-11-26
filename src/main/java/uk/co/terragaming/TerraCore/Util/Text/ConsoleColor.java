package uk.co.terragaming.TerraCore.Util.Text;

import org.fusesource.jansi.Ansi;

public class ConsoleColor {
	public static final String RESET = Ansi.ansi().fg(Ansi.Color.DEFAULT).toString() + Ansi.ansi().reset();
	
	public static final String BLACK = Ansi.ansi().fg(Ansi.Color.BLACK).toString();
	public static final String BLUE = Ansi.ansi().fg(Ansi.Color.BLUE).toString();
	public static final String CYAN = Ansi.ansi().fg(Ansi.Color.CYAN).toString();
	public static final String GREEN = Ansi.ansi().fg(Ansi.Color.GREEN).toString();
	public static final String MAGENTA = Ansi.ansi().fg(Ansi.Color.MAGENTA).toString();
	public static final String RED = Ansi.ansi().fg(Ansi.Color.RED).toString();
	public static final String WHITE = Ansi.ansi().fg(Ansi.Color.WHITE).toString();
	public static final String YELLOW = Ansi.ansi().fg(Ansi.Color.YELLOW).toString();

}
