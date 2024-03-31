package com.theflames096.lootbag.item;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

import com.theflames096.lootbag.config.Config;
import com.theflames096.lootbag.loot.LootTables;

public class LootBagItem extends Item {

    private final ResourceLocation LOOT_TABLE;

    public LootBagItem(Properties properties,String tier) {
        super(properties);
        LOOT_TABLE=LootTables.register("gameplay/loot_bag_gift_"+tier);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (world.isClientSide || hand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
        }
        LootTable table = world.getServer().getLootData().getLootTable(LOOT_TABLE);
        LootParams params = (new LootParams.Builder((ServerLevel) world))
                .withLuck(player.getLuck())
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withParameter(LootContextParams.TOOL, itemstack)
                .withParameter(LootContextParams.ORIGIN, player.position())
                .create(LootContextParamSets.GIFT);
        List<ItemStack> loot = table.getRandomItems(params);
        NonNullList<ItemStack> filteredLoot = NonNullList.create();
        for (ItemStack itemStack : loot) {
            boolean shouldGet = true;
            for (String id : Config.BLACK_LIST.get()) {
                if (id.equals(itemStack.getItem().builtInRegistryHolder().key().location().toString())) {
                    shouldGet = false;
                    break;
                }
            }
            if (shouldGet) {
                filteredLoot.add(itemStack);
            }
        }
        
            for (ItemStack itemStack : filteredLoot) {
                giveItem(player, itemStack);
            }
        itemstack.shrink(1);
        return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
    }

    private boolean giveItem(Player player, ItemStack itemStack) {
        if (player.getInventory().getFreeSlot() >= 0) {
            player.addItem(itemStack);
            return true;
        } else {
            player.drop(itemStack, true);
            return false;
        }
    }
}
