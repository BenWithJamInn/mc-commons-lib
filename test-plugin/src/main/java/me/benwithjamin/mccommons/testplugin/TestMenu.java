package me.benwithjamin.mccommons.testplugin;

import me.benwithjamin.mccommons.menu.Button;
import me.benwithjamin.mccommons.menu.Menu;
import me.benwithjamin.mccommons.utilities.items.ItemBuilder;
import org.bukkit.Bukkit;
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
    private final String randomString;

    public TestMenu(Inventory inventory, Player player, String randomString) {
        super(inventory, player);
        this.randomString = randomString;
    }

    public TestMenu(Inventory inventory, Player player, Menu prevMenu, String randomString) {
        super(inventory, player, prevMenu);
        this.randomString = randomString;
    }

    @Override
    public void build() {
        final TestMenu finalThis = this;
        this.add(new Button(5, new ItemBuilder(Material.DIRT).setName(this.randomString).build()) {
            @Override
            public void onPress(Player player, InventoryClickEvent event) {
                player.sendMessage("You pressed the dirt button!");
                new TestMenu(player.getOpenInventory().getTopInventory(), player, finalThis, "Sub MENU!!!").open();
            }
        });
        this.add(new Button(1, new ItemStack(Material.DIAMOND)) {
            @Override
            public void onPress(Player player, InventoryClickEvent event) {
                player.sendMessage("You pressed the diamond button!");
                new SignMenu(player, Bukkit::broadcastMessage).open();
            }
        });
    }
}
