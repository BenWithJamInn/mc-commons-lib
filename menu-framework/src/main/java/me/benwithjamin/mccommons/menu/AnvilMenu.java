package me.benwithjamin.mccommons.menu;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

/*
 * Project: me.benwithjamin.mccommons.menu | Author: BenWithJamIn#4547
 * Created: 25/03/2023 at 18:37
 */
public class AnvilMenu extends Menu {
    private final String previewText;
    private final Consumer<String> submit;

    public AnvilMenu(Player player, String previewText, Consumer<String> onSubmit, Menu prevMenu) {
        super(createAnvil(player), player, prevMenu);
        this.previewText = previewText;
        this.submit = onSubmit;
    }

    @Override
    public void open() {
        MenuManager.getActiveMenus().put(this.getPlayer(), this);
        this.build();
    }

    @Override
    public void build() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(previewText);
        item.setItemMeta(meta);
        this.add(new Button(0, item));
    }

    public void submit(String text) {
        this.submit.accept(text);
    }

    /**
     * Creates an anvil inventory for the player
     *
     * @param player The player to create the anvil for
     *
     * @return The anvil inventory
     */
    public static Inventory createAnvil(Player player) {
        player.closeInventory();
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        int containerId = nmsPlayer.nextContainerCounter();
        FakeAnvil fakeAnvil = new FakeAnvil(nmsPlayer);
        nmsPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerId, "minecraft:anvil", new ChatMessage(""), 0));
        nmsPlayer.activeContainer = fakeAnvil;
        nmsPlayer.activeContainer.windowId = containerId;
        nmsPlayer.activeContainer.addSlotListener(nmsPlayer);
        return fakeAnvil.getBukkitView().getTopInventory();
    }

    /**
     * Fake anvil impl
     */
    private static class FakeAnvil extends ContainerAnvil {
        public FakeAnvil(EntityHuman entityHuman) {
            super(entityHuman.inventory, entityHuman.world, new BlockPosition(0, 0, 0), entityHuman);
        }

        @Override
        public boolean a(EntityHuman entityHuman) {
            return true;
        }
    }
}
