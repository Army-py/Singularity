package fr.army.singularity.serializer.impl;

import fr.army.singularity.serializer.AbstractSerializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayOutputStream;

public class InventorySerializer extends AbstractSerializer<ItemStack[]> {

    @Override
    public byte[] serialize(ItemStack[] object) {
        try {
            if (isEmpty(object)) {
                return new byte[0];
            }
            return super.serialize(object);
        } catch (final Exception exception) {
            throw new RuntimeException("Error during serialization", exception);
        }
    }

    private boolean isEmpty(ItemStack[] object) {
        for (ItemStack item : object) {
            if (item != null) {
                return false;
            }
        }
        return true;
    }
}
