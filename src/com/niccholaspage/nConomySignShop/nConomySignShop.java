package com.niccholaspage.nConomySignShop;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.niccholaspage.nConomy.nConomy;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class nConomySignShop extends JavaPlugin {
	public nConomy econHandler;
	public PermissionHandler Permissions;
	//Links the BasicBlockListener
    private final blockListener blockListener = new blockListener(this);
    @Override
	public void onDisable() {
		//Print "Basic Disabled" on the log.
		System.out.println("nConomy Disabled");
		
	}
    @Override
	public void onEnable() {
		//Create the pluginmanage pm.
		PluginManager pm = getServer().getPluginManager();
	    pm.registerEvent(Event.Type.BLOCK_RIGHTCLICKED, blockListener, Event.Priority.Normal, this);
	    pm.registerEvent(Event.Type.SIGN_CHANGE, blockListener, Event.Priority.Normal, this);
       //Get the infomation from the yml file.
        PluginDescriptionFile pdfFile = this.getDescription();
        //Hook into Permissions
        setupPermissions();
        //Hook into nConomy
        setupnConomy();
        //Print that the plugin has been enabled!
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}
    public boolean isInt(String string){
    	return econHandler.isInt(string);
    }
    public boolean has(Player player, String node){
    	return has(player, node, true);
    }
    public boolean hasNotOP(Player player, String node){
    	return has(player, node, false);
    }
    public boolean has(Player player, String node, boolean checkIfNotOP){
    	if (Permissions == null){
    		if (checkIfNotOP == true){
    		if (player.isOp()){
    			return true;
    		}
    		}else {
    			return true;
    		}
    	}else {
    		if (Permissions.has(player, node)){
    			return true;
    		}
    	}
    	return false;
    }
    private void setupnConomy(){
        Plugin test = this.getServer().getPluginManager().getPlugin("nConomy");
            if (test != null) {
                this.econHandler = ((nConomy)test);
            } else {
            	System.out.println("nConomy not detected, disabling nConomySignShop.");
            	getPluginLoader().disablePlugin(this);
            }
    }
    private void setupPermissions() {
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.Permissions == null) {
            if (test != null) {
                this.Permissions = ((Permissions)test).getHandler();
            } else {
            	System.out.println("Permissions not detected, Sign shops can only be created by OPs.");
            }
        }
    }
}
