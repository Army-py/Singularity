package fr.army.singularity.entity.impl;

import fr.army.singularity.entity.AbstractLoggerEntity;
import jakarta.persistence.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class PlayerHostLoggerEntity extends AbstractLoggerEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Id
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private PlayerLoggerEntity player;
    private String ip;


    public static PlayerHostLoggerEntity fromSnapshot(PlayerHostLoggerSnapshot snapshot) {
        return new PlayerHostLoggerEntity()
                .setIp(snapshot.ip())
                .setPlayer(PlayerLoggerEntity.fromSnapshot(snapshot.player()));
    }

    public PlayerHostLoggerSnapshot toSnapshot() {
        return new PlayerHostLoggerSnapshot(getIp(), getPlayer().toSnapshot());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerHostLoggerEntity that)) return false;
        return getId().equals(that.getId()) && getPlayer().equals(that.getPlayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlayer().getId());
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
    private List<ConnectionLoggerEntity> connections = new ArrayList<>();

    public List<ConnectionLoggerEntity> getConnections() {
        return connections;
    }

    public void setConnections(List<ConnectionLoggerEntity> connections) {
        this.connections = connections;
    }

    public String getIp() {
        return ip;
    }

    public PlayerHostLoggerEntity setIp(String ip) {
        this.ip = ip;
        return this;
    }


    public record PlayerHostLoggerSnapshot(String ip, PlayerLoggerEntity.PlayerLoggerSnapshot player) implements Serializable {

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
        //         outDataStream.writeUTF(ip);
        //         outDataStream.write(player.writeToByte());
        //         outDataStream.flush();
        //     } catch (IOException e) {
        //         throw new RuntimeException(e);
        //     }
        //     return outByteStream.toByteArray();
        // }
    }
}
