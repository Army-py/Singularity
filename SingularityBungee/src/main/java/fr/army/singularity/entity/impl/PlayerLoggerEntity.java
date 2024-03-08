package fr.army.singularity.entity.impl;

import fr.army.singularity.entity.AbstractLoggerEntity;
import jakarta.persistence.*;

import java.io.*;
import java.util.*;

@Entity
public class PlayerLoggerEntity extends AbstractLoggerEntity implements Serializable {
    @Id
    private String id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private List<ConnectionLoggerEntity> connections = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private List<PlayerHostLoggerEntity> hosts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private List<BlockLoggerEntity> interactedBlocks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "player")
    private List<ItemLoggerEntity> interactedItems = new ArrayList<>();

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

    public UUID getUuid() {
        return UUID.fromString(id);
    }

    public String getId() {
        return id;
    }

    public PlayerLoggerEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public List<ConnectionLoggerEntity> getConnections() {
        return connections;
    }

    public void setConnections(List<ConnectionLoggerEntity> connections) {
        this.connections = connections;
    }

    public List<PlayerHostLoggerEntity> getHosts() {
        return hosts;
    }

    public PlayerLoggerEntity setHosts(List<PlayerHostLoggerEntity> hosts) {
        this.hosts = hosts;
        return this;
    }

    public List<BlockLoggerEntity> getInteractedBlocks() {
        return interactedBlocks;
    }

    public void setInteractedBlocks(List<BlockLoggerEntity> interactedBlocks) {
        this.interactedBlocks = interactedBlocks;
    }

    public List<ItemLoggerEntity> getInteractedItems() {
        return interactedItems;
    }

    public void setInteractedItems(List<ItemLoggerEntity> interactedItems) {
        this.interactedItems = interactedItems;
    }
}
