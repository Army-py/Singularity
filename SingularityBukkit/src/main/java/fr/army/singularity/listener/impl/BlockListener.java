package fr.army.singularity.listener.impl;

import fr.army.singularity.SingularityPlugin;
import fr.army.singularity.config.Config;
import fr.army.singularity.config.StorageMode;
import fr.army.singularity.database.StorageManager;
import fr.army.singularity.entity.action.BlockAction;
import fr.army.singularity.entity.impl.BlockLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import fr.army.singularity.network.channel.ChannelRegistry;
import fr.army.singularity.network.task.sender.AsyncDataSender;
import fr.army.singularity.serializer.SerializerManager;
import fr.army.singularity.serializer.impl.InventorySerializer;
import fr.army.singularity.serializer.impl.ItemStackSerializer;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Nullable;

public class BlockListener implements Listener {

    private final StorageManager storageManager;
    private final SerializerManager serializerManager;

    public BlockListener(SingularityPlugin plugin) {
        this.storageManager = plugin.getStorageManager();
        this.serializerManager = plugin.getSerializerManager();
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();

        saveBlockLogger(player, block, BlockAction.BREAK);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();

        saveBlockLogger(player, block, BlockAction.PLACE);
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        final Block block = event.getBlock();

        saveBlockLogger(null, block, BlockAction.BURN);
    }

    private void saveBlockLogger(@Nullable Player player, Block block, BlockAction action) {
        final AsyncDataSender asyncDataSender = new AsyncDataSender();

        final BlockLoggerEntity blockLoggerEntity = new BlockLoggerEntity()
                .setWorld(block.getWorld().getName())
                .setLocX(block.getX())
                .setLocY(block.getY())
                .setLocZ(block.getZ())
                .setBlock(block.getType().name())
                .setAction(action)
        ;
        if (block.getState() instanceof InventoryHolder){
            final byte[] content = serializerManager.serializeInventory(((InventoryHolder) block.getState()).getInventory().getContents());
            blockLoggerEntity.setContent(content);
        }

        if (player != null) {
            final PlayerLoggerEntity playerLoggerEntity = new PlayerLoggerEntity()
                    .setId(player.getUniqueId())
                    .setName(player.getName())
            ;
            blockLoggerEntity.setPlayer(playerLoggerEntity);
            playerLoggerEntity.getInteractedBlocks().add(blockLoggerEntity);

            if (Config.storageMode.equals(StorageMode.BUNGEE)){
                asyncDataSender.sendPluginMessage(playerLoggerEntity, ChannelRegistry.PLAYER_CHANNEL);
            } else {
                storageManager.savePlayerLogger(playerLoggerEntity);
            }
        } else {
            if (Config.storageMode.equals(StorageMode.BUNGEE)){
                asyncDataSender.sendPluginMessage(blockLoggerEntity, ChannelRegistry.BLOCK_CHANNEL);
            } else {
                storageManager.saveBlockLogger(blockLoggerEntity);
            }
        }
    }
}
