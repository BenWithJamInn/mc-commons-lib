package me.benwithjamin.menuframeworktestplugin;

import me.benwithjamin.menuframework.Button;
import me.benwithjamin.menuframework.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/*
 * Project: me.benwithjamin.menuframeworktestplugin | Author: BenWithJamIn#4547
 * Created: 09/03/2023 at 21:16
 */
public class TestMenu extends Menu {
    public TestMenu(Inventory inventory, Player player) {
        super(inventory, player);
    }

    @Override
    public void build() {
        this.add(new Button(1, new ItemStack(Material.DIAMOND)) {
            @Override
            public void onPress(Player player, InventoryClickEvent event) {
                player.sendMessage("You pressed the diamond button!");
            }
        });
    }
}
