package markehme.simpletownsxtras.commands;

import markehme.simpletownsxtras.SimpleTownsXtras;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jameshealey1994.simpletowns.SimpleTowns;
import com.gmail.jameshealey1994.simpletowns.commands.command.STCommand;
import com.gmail.jameshealey1994.simpletowns.localisation.Localisation;
import com.gmail.jameshealey1994.simpletowns.localisation.LocalisationEntry;
import com.gmail.jameshealey1994.simpletowns.object.Town;

public class CommandHome extends STCommand {

    /**
     * Constructor to add aliases and permissions.
     */
    public CommandHome() {
        this.aliases.add("home");
    }

    @Override
    public boolean execute(SimpleTowns plugin, CommandSender sender, String commandLabel, String[] args) {
        final Localisation localisation = plugin.getLocalisation();
        
        if(!SimpleTownsXtras.permission.has(sender, "SimpleTownsXtras.home")) {
        	 sender.sendMessage(localisation.get(LocalisationEntry.ERR_PERMISSION_DENIED));
        	return false;
        }
        
        String townname;
        
        Town town = null;
        
        switch (args.length) {
            case 0: {
                sender.sendMessage(localisation.get(LocalisationEntry.ERR_SPECIFY_TOWN_NAME));
                return false;
            }
            
            case 1: {
            	townname = args[0];
            	
                if (sender instanceof Player) {
                    town = plugin.getTown(townname);
                    
                    if (town == null) {
                   	 sender.sendMessage(localisation.get(LocalisationEntry.ERR_TOWN_NOT_FOUND, townname));
                       return false;
                   }
                    
                    townname = town.getName();
                } else {
                	// TODO: Should this be different?
                    sender.sendMessage(localisation.get(LocalisationEntry.ERR_SPECIFY_TOWN_NAME));
                    return false;
                }
                break;
            }
            
            default: {
                return false;
            }
        }
        
        // Check player is a member of town 
        if (!town.hasMember(sender.getName())) {
        	if(!sender.isOp()) {
        		// ops get bypass 
	            sender.sendMessage(localisation.get(LocalisationEntry.ERR_PLAYER_NOT_MEMBER, sender.getName(), town.getName()));
	            return true;
        	}
        }
        
        // TODO: check if home exists
        // TODO: teleport to home 

        return true;
    }
    
    @Override
    public String getDescription(Localisation localisation) {
        return localisation.get(LocalisationEntry.DESCRIPTION_ADD);
    }
}