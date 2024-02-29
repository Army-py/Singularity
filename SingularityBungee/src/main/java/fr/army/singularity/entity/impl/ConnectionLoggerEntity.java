package fr.army.singularity.entity.impl;

import fr.army.singularity.entity.AbstractLoggerEntity;
import jakarta.persistence.*;

import java.io.*;
import java.util.Date;


@Entity
public class ConnectionLoggerEntity extends AbstractLoggerEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    private double locX;
    private double locY;
    private double locZ;
    private String world;
    private String serverName;
    private int action;

    @ManyToOne(optional = false)
    private PlayerHostLoggerEntity playerHost;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private PlayerLoggerEntity player;


    @PrePersist
    public void prePersist() {
        this.date = new Date();
    }

    public static ConnectionLoggerEntity fromSnapshot(ConnectionLoggerSnapshot snapshot) {
        return new ConnectionLoggerEntity()
                .setDate(snapshot.date())
                .setLocX(snapshot.locX())
                .setLocY(snapshot.locY())
                .setLocZ(snapshot.locZ())
                .setWorld(snapshot.world())
                .setServerName(snapshot.serverName())
                .setAction(snapshot.action())
                .setPlayerHost(PlayerHostLoggerEntity.fromSnapshot(snapshot.playerHost()))
                .setPlayer(PlayerLoggerEntity.fromSnapshot(snapshot.player()));
    }

    public ConnectionLoggerSnapshot toSnapshot() {
        return new ConnectionLoggerSnapshot(getDate(), getLocX(), getLocY(), getLocZ(), getWorld(), getServerName(), getAction(), getPlayerHost().toSnapshot(), getPlayer().toSnapshot());
    }


    public Long getId() {
        return id;
    }

    public PlayerLoggerEntity getPlayer() {
        return player;
    }

    public ConnectionLoggerEntity setPlayer(PlayerLoggerEntity player) {
        this.player = player;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ConnectionLoggerEntity setDate(Date date) {
        this.date = date;
        return this;
    }

    public double getLocX() {
        return locX;
    }

    public ConnectionLoggerEntity setLocX(double locX) {
        this.locX = locX;
        return this;
    }

    public double getLocY() {
        return locY;
    }

    public ConnectionLoggerEntity setLocY(double locY) {
        this.locY = locY;
        return this;
    }

    public double getLocZ() {
        return locZ;
    }

    public ConnectionLoggerEntity setLocZ(double locZ) {
        this.locZ = locZ;
        return this;
    }

    public String getWorld() {
        return world;
    }

    public ConnectionLoggerEntity setWorld(String world) {
        this.world = world;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public ConnectionLoggerEntity setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public int getAction() {
        return action;
    }

    public ConnectionLoggerEntity setAction(int action) {
        this.action = action;
        return this;
    }

    public PlayerHostLoggerEntity getPlayerHost() {
        return playerHost;
    }

    public ConnectionLoggerEntity setPlayerHost(PlayerHostLoggerEntity playerHost) {
        this.playerHost = playerHost;
        return this;
    }


    public record ConnectionLoggerSnapshot(Date date, double locX, double locY, double locZ, String world,
                                           String serverName, int action, PlayerHostLoggerEntity.PlayerHostLoggerSnapshot playerHost,
                                           PlayerLoggerEntity.PlayerLoggerSnapshot player) implements Serializable {

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
        //         outDataStream.writeLong(date.getTime());
        //         outDataStream.writeDouble(locX);
        //         outDataStream.writeDouble(locY);
        //         outDataStream.writeDouble(locZ);
        //         outDataStream.writeUTF(world);
        //         outDataStream.writeUTF(serverName);
        //         outDataStream.writeInt(action);
        //         outDataStream.write(playerHost.writeToByte());
        //         outDataStream.write(player.writeToByte());
        //         outDataStream.flush();
        //     } catch (IOException e) {
        //         throw new RuntimeException(e);
        //     }
        //     return outByteStream.toByteArray();
        // }
    }
}
