package fr.army.singularity.serializer;

import fr.army.singularity.serializer.impl.InventorySerializer;
import fr.army.singularity.serializer.impl.ItemStackSerializer;
import org.bukkit.inventory.ItemStack;

public class SerializerManager {

    private final ItemStackSerializer itemStackSerializer;
    private final InventorySerializer inventorySerializer;

    public SerializerManager() {
        this.itemStackSerializer = new ItemStackSerializer();
        this.inventorySerializer = new InventorySerializer();
    }

    public byte[] serializeItemStack(ItemStack object) {
        return itemStackSerializer.serialize(object);
    }

    public ItemStack deserializeItemStack(byte[] bytes) {
        return itemStackSerializer.deserialize(bytes);
    }

    public byte[] serializeInventory(ItemStack[] object) {
        return inventorySerializer.serialize(object);
    }

    public ItemStack[] deserializeInventory(byte[] bytes) {
        return inventorySerializer.deserialize(bytes);
    }
}
