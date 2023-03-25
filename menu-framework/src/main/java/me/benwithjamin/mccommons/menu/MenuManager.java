package me.benwithjamin.mccommons.menu;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Project: me.benwithjamin.menuframework | Author: BenWithJamIn#4547
 * Created: 09/03/2023 at 20:59
 */
public class MenuManager implements Listener {
    @Getter private static JavaPlugin plugin;
    @Getter private static final Map<Player, Menu> activeMenus = new HashMap<>();
    private static final Map<Player, List<Menu>> menuTracks = new HashMap<>();

    public MenuManager(JavaPlugin plugin) {
        MenuManager.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Get the last menu in the track
     *
     * @param player The player to get the last menu for
     *
     * @return The last menu in the track or null of there is none
     */
    public static Menu getLastInTrack(Player player) {
        List<Menu> track = menuTracks.computeIfAbsent(player, k -> new ArrayList<>());
        if (track.isEmpty()) {
            return null;
        }
        int lastIndex = track.size() - 1;
        return track.get(lastIndex);
    }

    /**
     * Adds a menu to a players track
     *
     * @param player The player
     * @param menu The menu to add
     */
    public static void addMenuToTrack(Player player, Menu menu) {
        List<Menu> track = menuTracks.computeIfAbsent(player, k -> new ArrayList<>());
        track.add(menu);
    }

    /**
     * Removes the last element in the track
     *
     * @param player The player to remove last from track
     */
    public static void removeLastInTrack(Player player) {
        List<Menu> track = menuTracks.computeIfAbsent(player, k -> new ArrayList<>());
        int lastIndex = Math.max(track.size() - 1, 0);
        track.remove(lastIndex);
    }

    /**
     * Recovers the last menu in the track and opens it
     *
     * @param player The player to recover the last menu for
     */
    public static void recoverLastInTrack(Player player) {
        Menu lastInTrack = getLastInTrack(player);
        if (lastInTrack != null) {
            lastInTrack.open();
            removeLastInTrack(player);
        }
    }

    /**
     * Clears the track for a player
     *
     * @param player The player to clear the track for
     */
    public static void clearTrack(Player player) {
        menuTracks.computeIfAbsent(player, k -> new ArrayList<>()).clear();
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
        Menu lastInTrack = getLastInTrack(player);
        if (lastInTrack != null && lastInTrack != menu) {
            lastInTrack.open();
            removeLastInTrack(player);
        }
    }
}
