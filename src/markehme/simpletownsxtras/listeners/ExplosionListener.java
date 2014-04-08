package markehme.simpletownsxtras.listeners;


import java.util.ArrayList;

import markehme.simpletownsxtras.SimpleTownsXtras;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
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
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPreExplosionEvent(ExplosionPrimeEvent e) {
		Location l = e.getEntity().getLocation();
		
		String location = l.getWorld().getName() + " " + l.getChunk().getX() + " " + l.getChunk().getZ();
		
		// Add this information for later use
		e.getEntity().setMetadata("SimpleTownsXtras-primed", new FixedMetadataValue(SimpleTownsXtras.instance, location));
		
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onEntityExplode(EntityExplodeEvent e) {
		
		// Ensure there a blocks being blown up
		if(!(e.blockList().size() > 0)) {
			return;
		}
		
		// Creepers in Towns Block
		if(SimpleTownsXtras.config.getBoolean("explosions.disallowCreepersInTowns") && e.getEntity().getType() == EntityType.CREEPER) {
			Town townCreeperIn = SimpleTownsXtras.simpleTowns.getTown(e.getEntity().getLocation().getChunk());
			
			// If we're not in the wilderness, clear all blocks
			if(townCreeperIn != null) {
				e.blockList().clear();
			}
			
			return;
		}
		
		// TNT Fix 
		if(SimpleTownsXtras.config.getBoolean("explosions.disallowTNTIfFromOutsideTown") && e.getEntity().getType() == EntityType.PRIMED_TNT){
			
			// Grab the meta data we set in ExplosionPrimeEvent
			String[] metaData = e.getEntity().getMetadata("SimpleTownsXtras-primed").get(0).asString().split(" ");
			
			World worldIn = Bukkit.getWorld(metaData[0]);
			
			Chunk chunkIn = worldIn.getChunkAt(Integer.valueOf(metaData[1]), Integer.valueOf(metaData[2]));
			
			// Get town that it was primed in
			Town townPrimedIn = SimpleTownsXtras.simpleTowns.getTown(chunkIn);
			
			// work with a copy 
			ArrayList<Block> listOfBlocks = new ArrayList<Block>(e.blockList());
			
			// Loop through each block thats being destroyed and see if it's in a Town
			for(Block block : listOfBlocks) {
				Town currentTown = SimpleTownsXtras.simpleTowns.getTown(block.getChunk());
				
				// If no town, continue explosion
				if(currentTown == null)  {
					break;
				}
								
				// If the town names don't match, don't break the block
				if(townPrimedIn.getName() != currentTown.getName()) {
					e.blockList().remove(block);
				}
			}
		}
	}
}
