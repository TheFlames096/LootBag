package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.block.entity.LootBagEntity;
import cn.rainspace.lootbag.config.Config;
import cn.rainspace.lootbag.container.menu.LootBagMenu;
import cn.rainspace.lootbag.inventory.LootBagInventory;
import cn.rainspace.lootbag.loot.ModLootTables;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;
import java.util.Random;

public class LootBagItem extends Item {
    public LootBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!world.isClientSide && hand == InteractionHand.MAIN_HAND) {
            LootTable table = world.getServer().getLootTables().get(ModLootTables.LOOT_BAG_GIFT);
            LootContext context = (new LootContext.Builder((ServerLevel) world)).withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player).withParameter(LootContextParams.TOOL, itemstack).withParameter(LootContextParams.ORIGIN, player.position()).create(LootContextParamSets.GIFT);
            List<ItemStack> loot = table.getRandomItems(context);
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
            if (Config.SLOT_MODE.get()) {
                NetworkHooks.openScreen((ServerPlayer) player, new LootBagEntity((id, playerInventory, unused) -> {
                    return new LootBagMenu(MenuType.GENERIC_9x3, id, playerInventory, new LootBagInventory(filteredLoot), 3);
                }, Component.translatable("item.lootbag.loot_bag")));
            } else {
                for (ItemStack itemStack : filteredLoot) {
                    giveItem(player, itemStack);
                }
            }
            itemstack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
    }

    public boolean giveItem(Player player, ItemStack itemStack) {
        if (player.getInventory().getFreeSlot() >= 0) {
            player.addItem(itemStack);
            return true;
        } else {
            player.drop(itemStack, true);
            return false;
        }
    }

    @Mod.EventBusSubscriber()
    public static class LootBagEvent {

        @SubscribeEvent
        public static void onLivingSpawn(LivingSpawnEvent.SpecialSpawn event) {
            MobSpawnType mobSpawnType = event.getSpawnReason();
            if (mobSpawnType == MobSpawnType.NATURAL) {
                event.getEntity().addTag("natural");
            }
        }

        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            LivingEntity entity = event.getEntity();
            if (!entity.getType().getCategory().isFriendly() && entity.getLastHurtByMob() instanceof Player && (!Config.ONLY_DROP_BY_NATURAL_ENTITY.get() || entity.getTags().contains("natural"))) {
                Random random = new Random();
                if (random.nextInt(100) < Config.DROP_CHANCE.get()) {
                    ItemStack itemStack = new ItemStack(ModItems.LOOT_BAG.get());
                    entity.getLevel().getBiome(entity.getOnPos()).getTagKeys().forEach((e) -> {
                        itemStack.addTagElement(e.location().toString(), StringTag.valueOf(e.location().toString()));
                    });
                    entity.spawnAtLocation(itemStack);
                }
            }
        }
    }

}
