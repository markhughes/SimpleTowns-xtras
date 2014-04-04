package markehme.simpletownsxtras.listeners;


import markehme.simpletownsxtras.SimpleTownsXtras;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.gmail.jameshealey1994.simpletowns.object.Town;

public class ExplosionListener implements Listener {
	
	@EventHandler(priority=EventPriority.LOW)
	public void onPreExplosionEvent(ExplosionPrimeEvent e) {
		Location l = e.getEntity().getLocation();
		
		String location = l.getWorld().getName() + " " + l.getChunk().getX() + " " + l.getChunk().getZ();
		
		e.getEntity().setMetadata("SimpleTownsXtras-primed", new FixedMetadataValue(SimpleTownsXtras.instance, location));
		
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void onEntityExplode(EntityExplodeEvent e) {
				
		// TNT Fix 
		if(e.getEntity().getType() == EntityType.PRIMED_TNT && e.blockList().size() > 0){
				
			// Grab the meta data we set in ExplosionPrimeEvent
			String[] metaData = e.getEntity().getMetadata("SimpleTownsXtras-primed").get(0).asString().split(" ");
			
			// Get town that it was primed in
			Town townPrimedIn = SimpleTownsXtras.simpleTowns.getTown(Bukkit.getWorld(metaData[0]).getChunkAt(Integer.valueOf(metaData[1]), Integer.valueOf(metaData[2])));

			// Loop through each block thats being destroyed and see if it's in a Town
			for(Block block : e.blockList()){
				Town currentTown = SimpleTownsXtras.simpleTowns.getTown(block.getChunk());
				
				// If no town, continue explosion
				if(currentTown == null) return;
				
				// If not in same town as where to exploded, then remove
				if(townPrimedIn.getName() != currentTown.getName()) {
					e.blockList().remove(block);
				}
				
			}
		}
	}
}
