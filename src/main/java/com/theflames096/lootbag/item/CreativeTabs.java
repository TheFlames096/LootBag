package com.theflames096.lootbag.item;

import com.theflames096.lootbag.LootBag;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LootBag.MOD_ID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable("item.lootbag.loot_bag"))
            .icon(() -> Items.LOOT_BAG_STONE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(Items.LOOT_BAG_STONE.get());
                output.accept(Items.LOOT_BAG_STEAM.get());
                output.accept(Items.LOOT_BAG_LV.get());
                output.accept(Items.LOOT_BAG_MV.get());
                output.accept(Items.LOOT_BAG_HV.get());
                output.accept(Items.LOOT_BAG_EV.get());
                output.accept(Items.LOOT_BAG_IV.get());
                output.accept(Items.LOOT_BAG_LuV.get());
                output.accept(Items.LOOT_BAG_ZPM.get());
                output.accept(Items.LOOT_BAG_UV.get());
            }).build());
}
