package fr.army.singularity.serializer;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public abstract class AbstractSerializer<T> {

    protected byte[] serialize(T object){
        try {
            final ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

            final BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();

            return arrayOutputStream.toByteArray();
        } catch (final Exception exception) {
            throw new RuntimeException("Error during serialization", exception);
        }
    }

    protected T deserialize(byte[] bytes) {
        try {
            final ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
            final BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(arrayInputStream);
            return (T) objectInputStream.readObject();
        } catch (final Exception exception) {
            throw new RuntimeException("Error during deserialization", exception);
        }
    }
}
