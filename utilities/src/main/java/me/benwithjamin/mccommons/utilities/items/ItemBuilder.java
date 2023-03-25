package me.benwithjamin.mccommons.utilities.items;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.List;

/*
 * Project: me.benwithjamin.mccommons.utilities.items | Author: BenWithJamIn#4547
 * Created: 25/03/2023 at 14:03
 */
public class ItemBuilder {
    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(MaterialData materialData) {
        this.itemStack = materialData.toItemStack();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Sets the name of the item
     *
     * @param name The name
     * @return The item builder
     */
    public ItemBuilder setName(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(name);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the amount of the items tack
     *
     * @param amount The amount
     * @return The item builder
     */
    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Sets the lore of the item
     *
     * @param lore The lore
     * @return The item builder
     */
    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(lore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the lore of the item
     *
     * @param lore The lore
     * @return The item builder
     */
    public ItemBuilder setLore(String... lore) {
        this.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Adds lines to the end of the lore of the item
     *
     * @param lines The lines to add
     *
     * @return The item builder
     */
    public ItemBuilder addLoreLine(List<String> lines) {
        ItemMeta meta = this.itemStack.getItemMeta();
        List<String> lore = meta.getLore();
        lore.addAll(lines);
        meta.setLore(lore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Adds lines to the end of the lore of the item
     *
     * @param lines The lines to add
     *
     * @return The item builder
     */
    public ItemBuilder addLoreLine(String... lines) {
        this.addLoreLine(Arrays.asList(lines));
        return this;
    }

    /**
     * Adds an enchant to the item
     * Beware that this is unsafe and type of item and level must be checked etc.
     *
     * @param enchantment The enchantment
     * @param level       The level
     * @return The item builder
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Removes an enchant from the item
     *
     * @param enchantment The enchantment
     *
     * @return The item builder
     */
    public ItemBuilder removeEnchant(Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Adds an enchant glow effect to the item
     *
     * @param glow Whether the gow effect is enabled or not
     *
     * @return The item builder
     */
    public ItemBuilder glow(boolean glow) {
        if (!glow) {
            // remove glow
            this.itemStack.removeEnchantment(Enchantment.DAMAGE_ARTHROPODS);
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            this.itemStack.setItemMeta(meta);
            return this;
        }
        // add glow
        this.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1);
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the durability of the item
     *
     * @param durability The durability value
     *
     * @return The item builder
     */
    public ItemBuilder setDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    /**
     * Sets the dye color of the item
     *
     * @param dyeColor The dye color
     *
     * @return The item builder
     */
    public ItemBuilder setDyeColor(DyeColor dyeColor) {
        this.itemStack.setDurability(dyeColor.getDyeData());
        return this;
    }

    /**
     * Sets the NBT item tags
     *
     * @param nbtTagCompound The NBT tag compound
     *
     * @return The item builder
     */
    public ItemBuilder setNBTTagCompound(NBTTagCompound nbtTagCompound) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(this.itemStack);
        nmsStack.setTag(nbtTagCompound);
        this.itemStack = CraftItemStack.asBukkitCopy(nmsStack);
        return this;
    }

    /**
     * The item in its current state
     *
     * @return The ItemStack
     */
    public ItemStack build() {
        return this.itemStack;
    }
}
