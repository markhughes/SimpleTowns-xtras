package markehme.simpletownsxtras.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import markehme.simpletownsxtras.SimpleTownsXtras;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.jameshealey1994.simpletowns.events.TownClaimEvent;
import com.gmail.jameshealey1994.simpletowns.events.TownCreateEvent;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WorldGuardListener implements Listener {
	
	public static WorldGuardPlugin worldGuardPlugin = null;
	
	public WorldGuardListener() {
		worldGuardPlugin = WorldGuardPlugin.inst();
		
		SimpleTownsXtras.log("WorldGuard features enabled.");
	}
	
	@EventHandler
	public void onTownClaim(TownClaimEvent e) {
		
		if(!(e.getSender() instanceof Player)) {
			return;
		}
		
		if(isRegionThere((Player) e.getSender())) {
			e.getSender().sendMessage(ChatColor.RED + "You can't create a town here as there is a region in the way, please claim somewhere else.");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onTownCreate(TownCreateEvent e) {
		
		if(!(e.getSender() instanceof Player)) {
			return;
		}
		
		if(isRegionThere((Player) e.getSender())) {
			e.getSender().sendMessage(ChatColor.RED + "You can't create a town here as there is a region in the way, please claim somewhere else.");
			e.setCancelled(true);
		}
	}
	
	private boolean isRegionThere(Player checkPlayer) {
		
		World world = checkPlayer.getWorld();
		
		Chunk chunk = world.getChunkAt( checkPlayer.getLocation() );
			
		int minChunkX = chunk.getX() << 4;
		int minChunkZ = chunk.getZ() << 4;
		int maxChunkX = minChunkX + 15;
		int maxChunkZ = minChunkZ + 15;
		
		int worldHeight = world.getMaxHeight();

		BlockVector minChunk = new BlockVector( minChunkX, 0, minChunkZ );
		BlockVector maxChunk = new BlockVector( maxChunkX, worldHeight, maxChunkZ );
		
		RegionManager regionManager = worldGuardPlugin.getRegionManager( world );
		
		ProtectedCuboidRegion region = new ProtectedCuboidRegion( "__simpletownsxtras__checkregion", minChunk, maxChunk );
		
		Map<String, ProtectedRegion> allregions = regionManager.getRegions(); 
		
		List<ProtectedRegion> allregionslist = new ArrayList<ProtectedRegion>( allregions.values() );
		
		List<ProtectedRegion> overlaps;
		
		boolean foundregions = false;
		
		try {
			overlaps = region.getIntersectingRegions( allregionslist );
			if( overlaps == null || overlaps.isEmpty() ) {
				foundregions = false;
			} else {
				for(ProtectedRegion currentRegion : overlaps) {
					if(!currentRegion.getMembers().contains(checkPlayer.getName())) {
						foundregions = true;
					}
				}
			}
			
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
		
		region = null;
		allregionslist = null;
		overlaps = null;
		
		return foundregions;
	}
}
