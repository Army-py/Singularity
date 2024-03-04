package fr.army.singularity.network.reader;

import fr.army.singularity.database.StorageManager;
import fr.army.singularity.entity.impl.BlockLoggerEntity;
import fr.army.singularity.entity.impl.PlayerHostLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BlockLoggerReader {

    private final StorageManager storageManager;

    public BlockLoggerReader(StorageManager storageManager) {
        this.storageManager = storageManager;
    }


    public void read(byte[] data){
        final ByteArrayInputStream input = new ByteArrayInputStream(data);
        try {
            final ObjectInputStream inputStream = new ObjectInputStream(input);

            final Object object = inputStream.readObject();
            if (object instanceof BlockLoggerEntity blockEntity) {
                storageManager.saveBlockLogger(blockEntity);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
