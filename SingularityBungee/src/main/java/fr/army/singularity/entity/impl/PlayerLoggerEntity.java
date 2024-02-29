package fr.army.singularity.entity.impl;

import fr.army.singularity.entity.AbstractLoggerEntity;
import jakarta.persistence.*;

import java.io.*;
import java.util.Collection;
import java.util.UUID;

@Entity
public class PlayerLoggerEntity extends AbstractLoggerEntity implements Serializable {
    @Id
    private String id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Collection<ConnectionLoggerEntity> connections;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "player")
    private PlayerHostLoggerEntity host;


    public static PlayerLoggerEntity fromSnapshot(PlayerLoggerSnapshot snapshot){
        return new PlayerLoggerEntity()
                .setId(snapshot.id())
                .setName(snapshot.name());
    }

    public static PlayerLoggerEntity readFromByte(byte[] data) {
        final ByteArrayInputStream inByteStream = new ByteArrayInputStream(data);
        final DataInputStream inDataStream = new DataInputStream(inByteStream);
        try {
            final UUID id = UUID.fromString(inDataStream.readUTF());
            final String name = inDataStream.readUTF();
            return new PlayerLoggerEntity()
                    .setId(id)
                    .setName(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerLoggerSnapshot toSnapshot() {
        return new PlayerLoggerSnapshot(getId(), getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerLoggerEntity that)) return false;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }


    public PlayerLoggerEntity setId(UUID id) {
        this.id = id.toString();
        return this;
    }

    public UUID getId() {
        return UUID.fromString(id);
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

    public PlayerHostLoggerEntity getHost() {
        return host;
    }

    public PlayerLoggerEntity setHost(PlayerHostLoggerEntity host) {
        this.host = host;
        return this;
    }


    public record PlayerLoggerSnapshot(UUID id, String name) implements Serializable {

        public byte[] writeToByte() {
            final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            final ObjectOutputStream outDataStream;
            try {
                outDataStream = new ObjectOutputStream(outByteStream);
                outDataStream.writeObject(this);
                outDataStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return outByteStream.toByteArray();
        }

        // public byte[] writeToByte() {
        //     final ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        //     final DataOutputStream outDataStream = new DataOutputStream(outByteStream);
        //     try {
        //         outDataStream.writeUTF(id.toString());
        //         outDataStream.writeUTF(name);
        //         outDataStream.flush();
        //     } catch (IOException e) {
        //         throw new RuntimeException(e);
        //     }
        //     return outByteStream.toByteArray();
        // }
    }
}
