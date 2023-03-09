package me.benwithjamin.menuframework;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/*
 * Project: me.benwithjamin.menuframework | Author: BenWithJamIn#4547
 * Created: 09/03/2023 at 20:47
 */
@Getter
public class Button implements Cloneable {
    @Setter private int slot;
    private final ItemStack item;

    public Button(int slot, ItemStack item) {
        this.slot = slot;
        this.item = item;
    }

    /**
     * Called when the button is pressed
     *
     * @param player The player who pressed the button
     * @param event The event that was called
     */
    public void onPress(Player player, InventoryClickEvent event) { }

    @Override
    public Button clone() {
        try {
            return (Button) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
