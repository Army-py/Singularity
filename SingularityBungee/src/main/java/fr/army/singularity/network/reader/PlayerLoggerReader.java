package fr.army.singularity.network.reader;

import fr.army.singularity.database.StorageManager;
import fr.army.singularity.entity.impl.PlayerHostLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PlayerLoggerReader {

    private final StorageManager storageManager;

    public PlayerLoggerReader(@NotNull StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    public void read(byte[] data){
        final ByteArrayInputStream input = new ByteArrayInputStream(data);
        try {
            final ObjectInputStream inputStream = new ObjectInputStream(input);

            final Object object = inputStream.readObject();
            if (object instanceof PlayerLoggerEntity playerEntity) {
                storageManager.savePlayerLogger(playerEntity);
            } else if (object instanceof PlayerHostLoggerEntity hostEntity) {
                storageManager.savePlayerHostLogger(hostEntity);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
