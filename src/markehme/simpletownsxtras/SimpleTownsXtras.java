package markehme.simpletownsxtras;

import java.util.logging.Level;


import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleTownsXtras extends JavaPlugin {
	
	public static Plugin instance;
	
	@Override
	public void onEnable() {
		
		// Store current instance for later
		instance = this;
		
		// Setup our listeners 
		SimpleTownsXtrasListeners.setup();
	}
	
	public void log(String s) {
		getLogger().log(Level.INFO, s);
	}
	
}
