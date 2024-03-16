package fr.army.singularity.entity.impl;

import fr.army.singularity.entity.AbstractLoggerEntity;
import jakarta.persistence.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;


@Entity
public class ConnectionLoggerEntity extends AbstractLoggerEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double locX;
    private double locY;
    private double locZ;
    private String world;
    private String serverName;
    private int action;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PlayerHostLoggerEntity playerHost;


    @PrePersist
    public void prePersist() {
        this.date = new Date();
    }


    public Long getId() {
        return id;
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
}
