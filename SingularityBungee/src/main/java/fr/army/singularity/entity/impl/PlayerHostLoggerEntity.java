package fr.army.singularity.entity.impl;

import fr.army.singularity.entity.AbstractLoggerEntity;
import jakarta.persistence.*;

import java.io.*;
import java.util.*;

@Entity
public class PlayerHostLoggerEntity extends AbstractLoggerEntity implements Serializable {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    private PlayerLoggerEntity player;

    @Id
    private String ip;

    @OneToMany(mappedBy = "playerHost", cascade = CascadeType.ALL)
    private List<ConnectionLoggerEntity> connections = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerHostLoggerEntity that)) return false;
        return getIp().equals(that.getIp()) && getPlayer().equals(that.getPlayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIp(), getPlayer().getUuid());
    }

    public PlayerLoggerEntity getPlayer() {
        return player;
    }

    public PlayerHostLoggerEntity setPlayer(PlayerLoggerEntity player) {
        this.player = player;
        return this;
    }

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
}
