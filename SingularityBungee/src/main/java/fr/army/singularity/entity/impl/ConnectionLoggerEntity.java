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
    private int locX;
    private int locY;
    private int locZ;
    private String world;
    private String serverName;
    private int action;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private PlayerLoggerEntity player;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public PlayerLoggerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerLoggerEntity player) {
        this.player = player;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getLocX() {
        return locX;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public int getLocY() {
        return locY;
    }

    public void setLocY(int locY) {
        this.locY = locY;
    }

    public int getLocZ() {
        return locZ;
    }

    public void setLocZ(int locZ) {
        this.locZ = locZ;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
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
