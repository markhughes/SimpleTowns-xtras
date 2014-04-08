package markehme.simpletownsxtras;

import markehme.simpletownsxtras.listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.PluginManager;

import com.gmail.jameshealey1994.simpletowns.events.TownClaimEvent;
import com.gmail.jameshealey1994.simpletowns.events.TownCreateEvent;

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
		
		// If ChunkVisualizer is enabled, then enable our ChunkVisualizer features
		if(pm.getPlugin("ChunkVisualizer") != null) {
			if(pm.getPlugin("ChunkVisualizer").isEnabled()) {
				register(new ChunkVisualizerListener());
			}
		}
		
		// Explosion related features
		if(SimpleTownsXtras.config.getBoolean("explosions.disallowTNTIfFromOutsideTown") || SimpleTownsXtras.config.getBoolean("explosions.disallowCreepersInTowns")) {
			register(new ExplosionListener());
		}
		
		
	}
	
	/**
	 * Removes events we registered 
	 */
	public void deRegister() {
		TownClaimEvent.getHandlerList().unregister(SimpleTownsXtras.instance); 
		TownCreateEvent.getHandlerList().unregister(SimpleTownsXtras.instance); 
		EntityExplodeEvent.getHandlerList().unregister(SimpleTownsXtras.instance);
	}
	
	/** 
	 * Registers an event listener
	 * @param o
	 */
	private static void register(Listener o) {
		pm.registerEvents(o, SimpleTownsXtras.instance);
	}
}
