package me.benwithjamin.mccommons.menu;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/*
 * Project: me.benwithjamin.menuframework | Author: BenWithJamIn#4547
 * Created: 09/03/2023 at 20:59
 */
public class MenuManager implements Listener {
    @Getter private static JavaPlugin plugin;
    @Getter private static final Map<Player, Menu> activeMenus = new HashMap<>();

    public MenuManager(JavaPlugin plugin) {
        MenuManager.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlot() == -999 || event.getClickedInventory() == null) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Menu menu = activeMenus.get(player);
        if (menu == null) {
            return;
        }
        // now we know it is menu prevent all item movement
        event.setCancelled(true);
        Button button = menu.getButtons().get(event.getSlot());
        if (button == null) {
            return;
        }
        button.onPress(player, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Menu menu = activeMenus.get(player);
        if (menu == null) {
            return;
        }
        activeMenus.remove(player);
    }
}
