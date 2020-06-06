package RPGProspecting.environment.salvage;

import org.bukkit.Material;

public class SalvageDropHandler {

	public static final Salvage[] DEAD_BUSH = {
		new Salvage(Material.STICK, 0.75f, 2, false), new Salvage(Material.IRON_NUGGET, 0.05f, 1, false)
	};
	
	public static final Salvage[] CRATE = {
		new Salvage(Material.COOKED_BEEF, 0.5f, 2, false), new Salvage(Material.ACACIA_LOG, 0.25f, 1, false)
	};
}
