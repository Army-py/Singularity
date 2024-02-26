package fr.army.singularity.entity.impl;

import fr.army.singularity.entity.AbstractLoggerEntity;
import fr.army.singularity.entity.IEntity;
import jakarta.persistence.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;


@Entity
public class ConnectionLoggerEntity extends AbstractLoggerEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    private String ip;
    private double locX;
    private double locY;
    private double locZ;
    private String world;
    private String serverName;
    private int action;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private PlayerLoggerEntity player;

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

    public String getIp() {
        return ip;
    }

    public ConnectionLoggerEntity setIp(String ip) {
        this.ip = ip;
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

    @PrePersist
    public void prePersist() {
        this.date = new Date();
    }

    public static ConnectionLoggerEntity readFromByte(byte[] data) {
        try {
            final ObjectInputStream inDataStream = new ObjectInputStream(new ByteArrayInputStream(data));
            return (ConnectionLoggerEntity) inDataStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
