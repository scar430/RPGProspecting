package RPGProspecting.environment.salvage.listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import RPGProspecting.environment.salvage.Salvage;
import RPGProspecting.environment.salvage.SalvageUtil;
import RPGProspecting.environment.salvage.SalvageMath;

public class SalvageListener implements Listener {

	private ArrayList<String> doingTasks = new ArrayList<String>();
	
	private ArrayList<Location> lootedCrates = new ArrayList<Location>();
	
	private JavaPlugin plugin;
	
	public SalvageListener(JavaPlugin newPlugin) {
		plugin = newPlugin;
	}
	
	@EventHandler (ignoreCancelled = true , priority = EventPriority.HIGHEST)
	public void onSalvage(PlayerInteractEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (event.getHand() != EquipmentSlot.HAND) return;
		if (findPlayer(event.getPlayer().getDisplayName()) == true) return;
		
		// If player is already doing a task.
		if (doingTasks.contains(event.getPlayer().getName())) {
			event.setCancelled(true);
			return;
		}
		else {
			//System.out.print(findPlayer(event.getPlayer().getName()));
			
			// Mark player as committing a task.
			doingTasks.add(event.getPlayer().getName());
			
			roll(event);
			
			doingTasks.remove(event.getPlayer().getName());
			return;
		}
	}
	
	// Roll for loot and return item stack
	private void roll(PlayerInteractEvent event) {
		
		switch(event.getClickedBlock().getType()) {
		
		case SEA_PICKLE:
			
			loot(event.getPlayer(), SalvageUtil.SEA_PICKLE, "Canned Soup");
			
			break;
		
		case DEAD_BUSH:
			
			if (isLooted(event.getClickedBlock())) {
				event.getPlayer().sendMessage("§cThis has already been looted.");
				return;
			}
			
			loot(event.getPlayer(), SalvageUtil.DEAD_BUSH, "Dried Bush");
			
			respawnCrate(event.getClickedBlock());
			
			break;
			
		// Crate
		case ORANGE_GLAZED_TERRACOTTA:
			
			if (event.getItem().getType() != Material.WOODEN_SWORD) break;
			
			System.out.print(isLooted(event.getClickedBlock()));
			if (isLooted(event.getClickedBlock())) {
				event.getPlayer().sendMessage("§cThis has already been looted.");
				break;
			}
			
			loot(event.getPlayer(), SalvageUtil.CRATE, "Crate");
			
			respawnCrate(event.getClickedBlock());
			break;
			
		// Metal crate
		case MAGENTA_GLAZED_TERRACOTTA:
			
			if (event.getItem().getType() != Material.WOODEN_SWORD) break;
			
			if (isLooted(event.getClickedBlock())) {
				event.getPlayer().sendMessage("§cThis has already been looted.");
				break;
			}
			
			loot(event.getPlayer(), SalvageUtil.METAL_CRATE, "Metal Crate");
			
			respawnCrate(event.getClickedBlock());
			
		// Military crate, no cooldown for looting since it requires key anyways
		case LIGHT_BLUE_GLAZED_TERRACOTTA:
			
			if (event.getItem().getType() != Material.SEAGRASS) break;
			
			if (isLooted(event.getClickedBlock())) {
				event.getPlayer().sendMessage("§cThis has already been looted.");
				return;
			}
			
			loot(event.getPlayer(), SalvageUtil.MILITARY_CRATE, "Military Crate");
			
			respawnCrate(event.getClickedBlock());
			
			event.getPlayer().getInventory().removeItem(new ItemStack(Material.SEAGRASS));
			
			break;
			
			default:
				break;
		}
	}
	
	private void loot(Player player, Salvage[] salvages, String name) {
		
		// Timer
		
		doingTasks.add(player.getDisplayName());
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
            	player.sendMessage("§c1...");
            }
        }, 20);
		
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
            	player.sendMessage("§c2...");
            }
        }, 40);
		
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
            	player.sendMessage("§c3...");
            }
        }, 60);
		
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
            	player.sendMessage("§cYou've successfully looted a §c" + name + "§c.");
            	// Roll for drop on every salvage item.
    			for (Salvage salvage : salvages) {
    				// Roll and add item.
    				player.getInventory().addItem(SalvageMath.DropMath(salvage));
    			}
    			doingTasks.remove(player.getDisplayName());
            }
        }, 80);
	}
	
	
	private boolean findPlayer(String player) {
		for (String name : doingTasks) {
			if (name == player) {
				return true;
			}
			else {
				continue;
			}
		}
		return false;
	}
	
	private String getPlayer(String player) {
		for (String name : doingTasks) {
			if (name == player) {
				return name;
			}
			else {
				continue;
			}
		}
		return null;
	}
	
	// After 2 minecraft days the item respawns
	public void respawnCrate(Block block) {
		
		Location savedCrate = block.getLocation();
		
		lootedCrates.add(savedCrate);
		
		System.out.print(lootedCrates);
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
            	lootedCrates.remove(savedCrate);
            }
        }, 48000);
	}
	
	public boolean isLooted(Block block) {
		if (lootedCrates.contains(block.getLocation())) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
