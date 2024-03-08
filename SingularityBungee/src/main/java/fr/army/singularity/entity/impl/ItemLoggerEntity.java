package fr.army.singularity.entity.impl;

import fr.army.singularity.entity.AbstractLoggerEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class ItemLoggerEntity extends AbstractLoggerEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String world;
    private int locX;
    private int locY;
    private int locZ;
    private String itemType;
    private int amount;
    private byte[] itemData;
    private String action;
    private Date date;
    private String entity;
    @ManyToOne(cascade = CascadeType.MERGE)
    private PlayerLoggerEntity player;


    @PrePersist
    public void prePersist() {
        date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorld() {
        return world;
    }

    public ItemLoggerEntity setWorld(String world) {
        this.world = world;
        return this;
    }

    public int getLocX() {
        return locX;
    }

    public ItemLoggerEntity setLocX(int locX) {
        this.locX = locX;
        return this;
    }

    public int getLocY() {
        return locY;
    }

    public ItemLoggerEntity setLocY(int locY) {
        this.locY = locY;
        return this;
    }

    public int getLocZ() {
        return locZ;
    }

    public ItemLoggerEntity setLocZ(int locZ) {
        this.locZ = locZ;
        return this;
    }

    public String getItemType() {
        return itemType;
    }

    public ItemLoggerEntity setItemType(String item) {
        this.itemType = item;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public ItemLoggerEntity setAmount(int itemAmount) {
        this.amount = itemAmount;
        return this;
    }

    public byte[] getItemData() {
        return itemData;
    }

    public ItemLoggerEntity setItemData(byte[] itemData) {
        this.itemData = itemData;
        return this;
    }

    public String getAction() {
        return action;
    }

    public ItemLoggerEntity setAction(String action) {
        this.action = action;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ItemLoggerEntity setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getEntity() {
        return entity;
    }

    public ItemLoggerEntity setEntity(String entity) {
        this.entity = entity;
        return this;
    }

    public PlayerLoggerEntity getPlayer() {
        return player;
    }

    public ItemLoggerEntity setPlayer(PlayerLoggerEntity player) {
        this.player = player;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemLoggerEntity that = (ItemLoggerEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
