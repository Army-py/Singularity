package fr.army.singularity.listener.impl;

import fr.army.singularity.SingularityPlugin;
import fr.army.singularity.config.Config;
import fr.army.singularity.config.StorageMode;
import fr.army.singularity.database.StorageManager;
import fr.army.singularity.entity.action.ItemAction;
import fr.army.singularity.entity.impl.ItemLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import fr.army.singularity.network.channel.ChannelRegistry;
import fr.army.singularity.network.task.sender.QueuedDataSender;
import fr.army.singularity.serializer.ItemStackSerializer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemLoggerListener implements Listener {

    private final StorageManager storageManager;

    public ItemLoggerListener(SingularityPlugin plugin) {
        this.storageManager = plugin.getStorageManager();
    }


    // TODO reduce data send size (cf serializeToByte)

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        final LivingEntity entity = event.getEntity();
        final EntityType entityType = entity.getType();
        final Item item = event.getItem();
        final Player player;
        if (event.getEntity() instanceof Player) {
            player = (Player) event.getEntity();
        } else {
            player = null;
        }

        saveItemLogger(player, item, entityType, ItemAction.PICKUP);
    }

    @EventHandler
    public void onEntityDropItem(EntityDropItemEvent event) {
        final Entity entity = event.getEntity();
        final EntityType entityType = entity.getType();
        final Item item = event.getItemDrop();
        final Player player;
        if (event.getEntity() instanceof Player) {
            player = (Player) event.getEntity();
        } else {
            player = null;
        }

        saveItemLogger(player, item, entityType, ItemAction.DROP);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        final Item item = event.getItemDrop();
        final EntityType entityType = player.getType();

        saveItemLogger(player, item, entityType, ItemAction.DROP);
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        final Item item = event.getEntity();

        saveItemLogger(null, item, null, ItemAction.DESPAWN);
    }



    private void saveItemLogger(@Nullable Player player, Item item, @Nullable EntityType entityType, ItemAction action) {
        final QueuedDataSender queuedDataSender = new QueuedDataSender();
        final ItemStackSerializer itemStackSerializer = new ItemStackSerializer();
        final ItemStack itemStack = item.getItemStack();

        final ItemLoggerEntity itemLoggerEntity = new ItemLoggerEntity()
                .setWorld(item.getWorld().getName())
                .setLocX(item.getLocation().getBlockX())
                .setLocY(item.getLocation().getBlockY())
                .setLocZ(item.getLocation().getBlockZ())
                .setItemType(itemStack.getType().name())
                .setAmount(itemStack.getAmount())
                .setItemData(itemStackSerializer.serializeToByte(itemStack))
                .setAction(action.name())
        ;
        if (entityType != null) {
            itemLoggerEntity.setEntity(entityType.name());
        }

        if (player != null) {
            final PlayerLoggerEntity playerLoggerEntity = new PlayerLoggerEntity()
                    .setId(player.getUniqueId())
                    .setName(player.getName())
            ;
            itemLoggerEntity.setPlayer(playerLoggerEntity);
            playerLoggerEntity.getInteractedItems().add(itemLoggerEntity);

            if (Config.storageMode.equals(StorageMode.BUNGEE)){
                queuedDataSender.sendPluginMessage(playerLoggerEntity, ChannelRegistry.PLAYER_CHANNEL, 5);
            } else {
                storageManager.savePlayerLogger(playerLoggerEntity);
            }
        } else {
            if (Config.storageMode.equals(StorageMode.BUNGEE)){
                queuedDataSender.sendPluginMessage(itemLoggerEntity, ChannelRegistry.ITEM_CHANNEL, 5);
            } else {
                storageManager.saveItemLogger(itemLoggerEntity);
            }
        }
    }
}
