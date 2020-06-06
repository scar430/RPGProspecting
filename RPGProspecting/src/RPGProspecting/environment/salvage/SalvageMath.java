package RPGProspecting.environment.salvage;

import java.util.Random;

import org.bukkit.inventory.ItemStack;

public class SalvageMath {
	
	public static ItemStack DropMath(Salvage salvage) {
		
		Random rand = new Random();
		
		// Roll for drop chance.
		// Passed drop chance.
		if (rand.nextFloat() < salvage.chance) {
			
			// Pointer to random amount.
			int amount = rand.nextInt(salvage.amount);
			
			// If amount rolled to 0 and there needs to be a drop then return at least one
			if (salvage.absolute == true && amount == 0)
			{
				return new ItemStack(salvage.drop, 1);
			}
			// return whatever the amount was, even if 0
			else {
				return new ItemStack(salvage.drop, amount);
			}
		}
		// return at least one if there needs to be a drop
		else if(salvage.absolute == true) {
			return new ItemStack(salvage.drop, 1);
		}
		// Drops aren't absolute and failed drop chance, no reward.
		else {
			return new ItemStack(salvage.drop, 0);
		}
	}
}
