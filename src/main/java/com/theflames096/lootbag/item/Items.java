package com.theflames096.lootbag.item;

import com.theflames096.lootbag.LootBag;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LootBag.MOD_ID);

    public static final RegistryObject<Item> LOOT_BAG_STONE = ITEMS.register("loot_bag_stone", () -> new LootBagItem(new Properties().stacksTo(1),"stone"));
    public static final RegistryObject<Item> LOOT_BAG_STEAM = ITEMS.register("loot_bag_steam", () -> new LootBagItem(new Properties().stacksTo(1),"steam"));
    public static final RegistryObject<Item> LOOT_BAG_LV = ITEMS.register("loot_bag_lv", () -> new LootBagItem(new Properties().stacksTo(1),"lv"));
    public static final RegistryObject<Item> LOOT_BAG_MV = ITEMS.register("loot_bag_mv", () -> new LootBagItem(new Properties().stacksTo(1),"mv"));
    public static final RegistryObject<Item> LOOT_BAG_HV = ITEMS.register("loot_bag_hv", () -> new LootBagItem(new Properties().stacksTo(1),"hv"));
    public static final RegistryObject<Item> LOOT_BAG_EV = ITEMS.register("loot_bag_ev", () -> new LootBagItem(new Properties().stacksTo(1),"ev"));
    public static final RegistryObject<Item> LOOT_BAG_IV = ITEMS.register("loot_bag_iv", () -> new LootBagItem(new Properties().stacksTo(1),"iv"));
    public static final RegistryObject<Item> LOOT_BAG_LuV = ITEMS.register("loot_bag_luv", () -> new LootBagItem(new Properties().stacksTo(1),"luv"));
    public static final RegistryObject<Item> LOOT_BAG_ZPM = ITEMS.register("loot_bag_zpm", () -> new LootBagItem(new Properties().stacksTo(1),"zpm"));
    public static final RegistryObject<Item> LOOT_BAG_UV = ITEMS.register("loot_bag_uv", () -> new LootBagItem(new Properties().stacksTo(1),"uv"));
    

}
