package RPGProspecting.environment.ambient.itemframe;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class PickupItemListener implements Listener {

	private JavaPlugin plugin;
	
	public PickupItemListener(JavaPlugin newPlugin) {
		plugin = newPlugin;
	}
	
	@EventHandler
	public void onPickup(EntityDamageByEntityEvent e) {
		// Asinine behaviors, return.
		if (e.getDamager().getType() != EntityType.PLAYER) return;
		
		Player player = (Player) e.getDamager();
		if (player.getGameMode() == GameMode.CREATIVE) return;
		
		// This is retarded.
		ItemFrame frame = (ItemFrame) e.getEntity();
		
		// Check if the item frame has an acceptable item, otherwise don't pick it up.
		for (Material type : PickupItemUtil.acceptableItems) {
			
			// Check item type in itemframe
			if (frame.getItem().getType() == type) {
				// Pickup was successful, send a message to the player.
				player.sendMessage("§cYou successfully picked up an item.");
				player.getInventory().addItem(frame.getItem());
				respawnItem(frame, frame.getItem());
				frame.setItem(new ItemStack(Material.AIR));
				return;
			}
			else {
				// Not appropriate item, continue searching.
				continue;
			}
		}
		
		// No items were found, cancel the event.
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onChange(PlayerInteractEntityEvent e) {
		// Cancel interactions with item frames for players in survival.
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		if (e.getRightClicked().getType() == EntityType.ITEM_FRAME) e.setCancelled(true);
	}
	
	// After 2 minecraft days the item respawns
	public void respawnItem(ItemFrame frame, ItemStack item) {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
            	frame.setItem(item);
            }
        }, 48000);
	}
}
