package RPGProspecting.environment.salvage;

import org.bukkit.Material;

public class SalvageUtil {

	public static final Salvage[] DEAD_BUSH = {
		new Salvage(Material.STICK, 0.75f, 2, false), 
		new Salvage(Material.IRON_NUGGET, 0.05f, 1, false)
	};
	
	public static final Salvage[] CRATE = {
		new Salvage(Material.BEETROOT_SOUP, 0.5f, 2, false),
		new Salvage(Material.WOODEN_AXE, 0.25f, 1, false)
	};
	
	public static final Salvage[] METAL_CRATE = {
		new Salvage(Material.LEATHER_BOOTS, 0.5f, 2, false), 
		new Salvage(Material.LEATHER_LEGGINGS, 0.75f, 2, false), 
		new Salvage(Material.LEATHER_CHESTPLATE, 0.25f, 2, false), 
		new Salvage(Material.LEATHER_HELMET, 0.75f, 2, false),
		new Salvage(Material.BEETROOT_SOUP, 0.5f, 2, false), // Can of Soup
		new Salvage(Material.MILK_BUCKET, 0.5f, 1, false) // Cure
	};
	
	public static final Salvage[] MILITARY_CRATE = {
		new Salvage(Material.IRON_BOOTS, 0.5f, 2, false), 
		new Salvage(Material.IRON_LEGGINGS, 0.75f, 2, false), 
		new Salvage(Material.IRON_CHESTPLATE, 0.25f, 2, false), 
		new Salvage(Material.IRON_HELMET, 0.75f, 2, false),
		new Salvage(Material.RABBIT_STEW, 0.5f, 1, false), // MRE
		new Salvage(Material.MILK_BUCKET, 0.75f, 1, false) // Cure
	};
	
	// Canned soup
	public static final Salvage[] SEA_PICKLE = {
		new Salvage(Material.BEETROOT_SOUP, 1.0f, 1, true)
	};
}
