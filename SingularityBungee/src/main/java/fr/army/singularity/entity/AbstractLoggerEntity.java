package fr.army.singularity.entity;

import fr.army.singularity.entity.impl.PlayerLoggerEntity;

import java.io.*;

public abstract class AbstractLoggerEntity implements IEntity {

    public byte[] writeToByte() {
        final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        final ObjectOutputStream outDataStream;
        try {
            outDataStream = new ObjectOutputStream(outByteStream);
            outDataStream.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outByteStream.toByteArray();
    }
}
