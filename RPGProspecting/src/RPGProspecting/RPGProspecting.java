package RPGProspecting;

import org.bukkit.plugin.java.JavaPlugin;

import RPGProspecting.environment.ambient.itemframe.PickupItemListener;
import RPGProspecting.environment.salvage.listener.SalvageListener;

public class RPGProspecting extends JavaPlugin {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new SalvageListener(this), this);
		getServer().getPluginManager().registerEvents(new PickupItemListener(this), this);
	}
	
	@Override
	public void onDisable() {
		
	}
}
