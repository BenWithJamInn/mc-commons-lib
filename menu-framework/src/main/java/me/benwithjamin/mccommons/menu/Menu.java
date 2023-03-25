package me.benwithjamin.mccommons.menu;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/*
 * Project: me.benwithjamin.menuframework | Author: BenWithJamIn#4547
 * Created: 09/03/2023 at 20:46
 */
@Getter
public abstract class Menu {
    private final HashMap<Integer, Button> buttons = new HashMap<>();
    private final Inventory inventory;
    private final Player player;

    public Menu(Inventory inventory, Player player) {
        this.inventory = inventory;
        this.player = player;
        MenuManager.clearTrack(this.player);
    }

    public Menu(Inventory inventory, Player player, @Nullable Menu prevMenu) {
        this.inventory = inventory;
        this.player = player;
        MenuManager.addMenuToTrack(player, prevMenu);
    }

    /**
     * Called when the menu buttons need to be placed
     */
    public abstract void build();

    /**
     * Opens the menu
     */
    public void open() {
        Inventory openInventory = this.player.getOpenInventory().getTopInventory();
        if (openInventory != null) {
            player.getOpenInventory().close();
        }
        this.build();
        MenuManager.getActiveMenus().put(this.player, this);
        new BukkitRunnable() {
            @Override
            public void run() {
                player.openInventory(inventory);
            }
        }.runTaskLater(MenuManager.getPlugin(), 1);
    }

    /**
     * Closes all menus for the player and does not recover from the track
     */
    public void close() {
        MenuManager.clearTrack(this.player);
        this.player.closeInventory();
    }

    /**
     * Add new button to the menu
     *
     * @param button The button to add
     */
    public void add(Button button) {
        this.buttons.put(button.getSlot(), button);
        this.inventory.setItem(button.getSlot(), button.getItem());
    }

    /**
     * Adds a button to the next empty slot in the menu
     *
     * @param button The button to add
     */
    public void addNextEmpty(Button button) {
        for (int i = 0; i < this.inventory.getSize(); i++) {
            if (this.inventory.getItem(i) == null) {
                button.setSlot(i);
                this.add(button);
                break;
            }
        }
    }

    /**
     * Fills the rest of the slots with the provided button
     *
     * @param button The button to fill the empty slots with
     */
    public void fillEmptySlots(Button button) {
        for (int i = 0; i < this.inventory.getSize(); i++) {
            if (this.inventory.getItem(i) == null) {
                Button clonedButton = button.clone();
                clonedButton.setSlot(i);
                this.add(clonedButton);
            }
        }
    }
}
