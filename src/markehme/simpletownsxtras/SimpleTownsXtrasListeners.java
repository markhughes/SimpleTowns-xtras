package markehme.simpletownsxtras;

import markehme.simpletownsxtras.listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class SimpleTownsXtrasListeners {

	private static PluginManager pm;
	
	
	/**
	 * Sets up event listeners that are required
	 */
	public static void setup() {
		
		pm = Bukkit.getPluginManager();
		
		// If WorldGuard is enabled, then enable our WorldGuard features 
		if(pm.getPlugin("WorldGuard") != null) {
			if(pm.getPlugin("WorldGuard").isEnabled()) {
				register(new WorldGuardListener());
			}
		}
	}
	
	/**
	 * Removes events we registered 
	 */
	public void deRegister() {
		
	}
	
	/** 
	 * Registers an event listener
	 * @param o
	 */
	private static void register(Listener o) {
		pm.registerEvents(o, SimpleTownsXtras.instance);
	}
}
