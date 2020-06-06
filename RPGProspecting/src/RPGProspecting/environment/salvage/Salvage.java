package RPGProspecting.environment.salvage;

import org.bukkit.Material;

public class Salvage {
	
	// Material to drop
	public Material drop;
	
	// Chance of it dropping from 0% to 100%
	public float chance;
	
	// Amount of the object that will drop.
	public int amount;
	
	// If this will always drop (removes the chance of this dropping 0)
	public boolean absolute;
	
	public Salvage(Material newDrop, float newChance, int newAmount, boolean isAbsolute) {
		drop = newDrop;
		chance = newChance;
		amount = newAmount;
		absolute = isAbsolute;
	}
}
