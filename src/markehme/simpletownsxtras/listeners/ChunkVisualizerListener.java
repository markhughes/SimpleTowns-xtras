package markehme.simpletownsxtras.listeners;

import markehme.simpletownsxtras.SimpleTownsXtras;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.jameshealey1994.simpletowns.events.TownClaimEvent;
import com.gmail.jameshealey1994.simpletowns.events.TownCreateEvent;

public class ChunkVisualizerListener implements Listener {
	
	public ChunkVisualizerListener() {
		
		SimpleTownsXtras.log("ChunkVisualizer features enabled.");
		
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onTownClaim(TownClaimEvent e) {
		
		if(SimpleTownsXtras.permission.playerHas((Player) e.getSender(), "SimpleTownsXtras.ChunkVisualizer.showonclaim")) {
			runChunkVisualizerCommand((Player) e.getSender());
		}
		
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onTownCreate(TownCreateEvent e) {
		
		if(!(e.getSender() instanceof Player)) {
			return;
		}
		
		if(SimpleTownsXtras.permission.playerHas((Player) e.getSender(), "SimpleTownsXtras.ChunkVisualizer.showonclaim")
				|| SimpleTownsXtras.permission.playerHas((Player) e.getSender(), "SimpleTownsXtras.ChunkVisualizer.showoncreate")) {
			runChunkVisualizerCommand((Player) e.getSender());
		}		
	}
	
	/**
	 * Performs the command /chunkvisualizer
	 * @param player
	 */
	public void runChunkVisualizerCommand(Player player) {
		
		player.performCommand("chunkvisualizer");
		
	}
}
