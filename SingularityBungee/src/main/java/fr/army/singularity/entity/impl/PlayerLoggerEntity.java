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
    private UUID id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Collection<ConnectionLoggerEntity> connections;

    public PlayerLoggerEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public PlayerLoggerEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
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
