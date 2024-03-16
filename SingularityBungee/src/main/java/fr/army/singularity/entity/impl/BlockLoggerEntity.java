package fr.army.singularity.entity.impl;

import fr.army.singularity.entity.AbstractLoggerEntity;
import fr.army.singularity.entity.action.BlockAction;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class BlockLoggerEntity extends AbstractLoggerEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String world;
    private int locX;
    private int locY;
    private int locZ;
    private String block;
    private String action;
    private Date date;
    private byte[] content;

    @ManyToOne(cascade = CascadeType.ALL)
    private PlayerLoggerEntity player;


    @PrePersist
    public void prePersist() {
        date = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getWorld() {
        return world;
    }

    public BlockLoggerEntity setWorld(String world) {
        this.world = world;
        return this;
    }

    public int getLocX() {
        return locX;
    }

    public BlockLoggerEntity setLocX(int locX) {
        this.locX = locX;
        return this;
    }

    public int getLocY() {
        return locY;
    }

    public BlockLoggerEntity setLocY(int locY) {
        this.locY = locY;
        return this;
    }

    public int getLocZ() {
        return locZ;
    }

    public BlockLoggerEntity setLocZ(int locZ) {
        this.locZ = locZ;
        return this;
    }

    public String getBlock() {
        return block;
    }

    public BlockLoggerEntity setBlock(String block) {
        this.block = block;
        return this;
    }

    public BlockAction getAction() {
        return BlockAction.valueOf(action);
    }

    public BlockLoggerEntity setAction(BlockAction action) {
        this.action = action.name();
        return this;
    }

    public Date getDate() {
        return date;
    }

    public BlockLoggerEntity setDate(Date date) {
        this.date = date;
        return this;
    }

    public byte[] getContent() {
        return content;
    }

    public BlockLoggerEntity setContent(byte[] content) {
        this.content = content;
        return this;
    }

    public PlayerLoggerEntity getPlayer() {
        return player;
    }

    public BlockLoggerEntity setPlayer(PlayerLoggerEntity player) {
        this.player = player;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockLoggerEntity that = (BlockLoggerEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
