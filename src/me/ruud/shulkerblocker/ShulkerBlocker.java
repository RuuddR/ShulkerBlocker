package me.ruud.shulkerblocker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.EnderChest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ShulkerBlocker extends JavaPlugin implements Listener {

    private FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        config.options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String item;
        if (event.getCurrentItem() != null)
            item = event.getCurrentItem().getType().toString();
        else
            return;
        Inventory clickedInventory = event.getClickedInventory();
        InventoryHolder inventoryHolder = event.getInventory().getHolder();

        if (clickedInventory == player.getInventory() && item.contains("SHULKER_BOX")) {
            if (inventoryHolder instanceof Chest) {
                Chest chest = (Chest) inventoryHolder;
                if (chest.getBlock().getType().toString().equals("CHEST") && !player.hasPermission("shulkerblocker.normal")
                        || chest.getBlock().getType().toString().equals("TRAPPED_CHEST") && !player.hasPermission("shulkerblocker.trapped")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("message").replaceAll("<CHEST>", chest.getBlock().getType().toString().toLowerCase().replaceAll("_", " "))));
                    event.setCancelled(true);
                }
            }

            if (inventoryHolder instanceof ChestedHorse && !player.hasPermission("shulkerblocker.donkey")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("message").replaceAll("<CHEST>", "donkey chest")));
                event.setCancelled(true);
            }

            if (event.getInventory().getType() == InventoryType.ENDER_CHEST && !player.hasPermission("shulkerblocker.ender")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("message").replaceAll("<CHEST>", "ender chest")));
                event.setCancelled(true);
            }

            if (inventoryHolder instanceof DoubleChest && !player.hasPermission("shulkerblocker.double")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("message").replaceAll("<CHEST>", "double chest")));
                event.setCancelled(true);
            }
        }
    }

}
