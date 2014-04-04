package markehme.simpletownsxtras;

import java.io.IOException;
import java.util.logging.Level;

import markehme.simpletownsxtras.utilities.Metrics;
import markehme.simpletownsxtras.utilities.Metrics.Graph;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jameshealey1994.simpletowns.SimpleTowns;

public class SimpleTownsXtras extends JavaPlugin {
	
	public static Plugin instance;
	
	public static Permission permission;
	
	public static SimpleTowns simpleTowns = null;
	
	private Metrics metrics;
	
	@Override
	public void onEnable() {
		
		// Store current instance for later
		instance = this;
		
		// Setup Vault
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration( net.milkbowl.vault.permission.Permission.class );
		if(permissionProvider != null) {
			permission = permissionProvider.getProvider();
		} else {
			log("Vault could not find an permission provider ... ");
		}
		
		// Setup our listeners 
		SimpleTownsXtrasListeners.setup();
		
		try {
			
			metrics = new Metrics(this);
			
            Graph SimpleTownsVersion = metrics.createGraph("SimpleTowns Version");
	        
            SimpleTownsVersion.addPlotter(new Metrics.Plotter(Bukkit.getPluginManager().getPlugin("SimpleTowns").getDescription().getVersion()) {

                @Override
                public int getValue() {
                    return 1;
                }
            });
            
			metrics.start();
			
		} catch (IOException e) {
			
			log("Metrics failed to not start up: " + e.getMessage());
			
		}
		
	}
	
	public static void log(String s) {
		Bukkit.getLogger().log(Level.INFO, "[SimpleTownsXtras] " + s);
	}
	
	public void addCommand() {
		simpleTowns = (SimpleTowns) Bukkit.getPluginManager().getPlugin("SimpleTowns");
		
		//simpleTowns.getCommandEnvironment(). = new CommandHome();
	}
	
}
