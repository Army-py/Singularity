package fr.army.singularity.entity.impl;

import fr.army.singularity.entity.AbstractLoggerEntity;
import fr.army.singularity.entity.IEntity;
import jakarta.persistence.*;

import java.io.*;
import java.util.Collection;
import java.util.UUID;

@Entity
public class PlayerLoggerEntity extends AbstractLoggerEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Collection<ConnectionLoggerEntity> connections;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public Collection<ConnectionLoggerEntity> getConnections() {
        return connections;
    }

    public void setConnections(Collection<ConnectionLoggerEntity> connections) {
        this.connections = connections;
    }

    public static PlayerLoggerEntity readFromByte(byte[] data) {
        try {
            final ObjectInputStream inDataStream = new ObjectInputStream(new ByteArrayInputStream(data));
            return (PlayerLoggerEntity) inDataStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
