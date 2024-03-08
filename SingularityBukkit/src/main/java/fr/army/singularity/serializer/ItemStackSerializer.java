package fr.army.singularity.serializer;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ItemStackSerializer {

    public byte[] serializeToByte(ItemStack itemStack) {
        try {
            final ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

            final BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(itemStack);
            objectOutputStream.flush();

            return arrayOutputStream.toByteArray();
        } catch (final Exception exception) {
            throw new RuntimeException("Error turning ItemStack into byte", exception);
        }
    }


    public ItemStack[] deserializeFromByte(byte[] bytes) {
        try {
            final ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
            final BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(arrayInputStream);
            return (ItemStack[]) objectInputStream.readObject();
        } catch (final Exception exception) {
            throw new RuntimeException("Error turning byte into ItemStack", exception);
        }
    }
}
