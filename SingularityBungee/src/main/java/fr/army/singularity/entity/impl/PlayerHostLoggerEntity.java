package fr.army.singularity.entity.impl;

import fr.army.singularity.entity.AbstractLoggerEntity;
import jakarta.persistence.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;

@Entity
public class PlayerHostLoggerEntity extends AbstractLoggerEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Id
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private PlayerLoggerEntity player;
    private String ip;


    public static PlayerHostLoggerEntity readFromByte(byte[] data) {
        try {
            final ObjectInputStream inDataStream = new ObjectInputStream(new ByteArrayInputStream(data));
            return (PlayerHostLoggerEntity) inDataStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public Long getId() {
        return id;
    }

    public PlayerLoggerEntity getPlayer() {
        return player;
    }

    public PlayerHostLoggerEntity setPlayer(PlayerLoggerEntity player) {
        this.player = player;
        return this;
    }

    @OneToMany(mappedBy = "playerHost")
    private Collection<ConnectionLoggerEntity> connections;

    public Collection<ConnectionLoggerEntity> getConnections() {
        return connections;
    }

    public void setConnections(Collection<ConnectionLoggerEntity> connections) {
        this.connections = connections;
    }

    public String getIp() {
        return ip;
    }

    public PlayerHostLoggerEntity setIp(String ip) {
        this.ip = ip;
        return this;
    }
}
