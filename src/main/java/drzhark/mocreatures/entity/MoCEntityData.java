/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.ArrayList;
import java.util.List;

public class MoCEntityData {

    private final MobSpawnInfo.Spawners spawnListEntry;
    private List<Type> biomeTypes;
    private List<Type> blockedBiomeTypes = new ArrayList<>();
    private EntityClassification typeOfCreature;
    private String entityName;
    private boolean canSpawn = true;
    private int entityId;
    private int frequency;
    private int minGroup;
    private int maxGroup;
    private int maxSpawnInChunk;
    private int[] dimensions;

    public MoCEntityData(String name, int maxchunk, int[] dimensions, EntityClassification type, MobSpawnInfo.Spawners spawnListEntry, List<Type> biomeTypes) {
        this.entityName = name;
        this.typeOfCreature = type;
        this.dimensions = dimensions;
        this.biomeTypes = biomeTypes;
        this.frequency = spawnListEntry.itemWeight;
        this.minGroup = spawnListEntry.minCount;
        this.maxGroup = spawnListEntry.maxCount;
        this.maxSpawnInChunk = maxchunk;
        this.spawnListEntry = spawnListEntry;
        MoCreatures.entityMap.put(spawnListEntry.type, this);
    }

    public MoCEntityData(String name, int maxchunk, int[] dimensions, EntityClassification type, MobSpawnInfo.Spawners spawnListEntry, List<Type> biomeTypes, List<Type> blockedBiomeTypes) {
        this.entityName = name;
        this.typeOfCreature = type;
        this.dimensions = dimensions;
        this.biomeTypes = biomeTypes;
        this.blockedBiomeTypes = blockedBiomeTypes;
        this.frequency = spawnListEntry.itemWeight;
        this.minGroup = spawnListEntry.minCount;
        this.maxGroup = spawnListEntry.maxCount;
        this.maxSpawnInChunk = maxchunk;
        this.spawnListEntry = spawnListEntry;
        MoCreatures.entityMap.put(spawnListEntry.type, this);
    }

    public EntityType<?> getEntityClass() {
        return this.spawnListEntry.type;
    }

    public EntityClassification getTypeMoC() {
        if (this.typeOfCreature != null) {
            return this.typeOfCreature;
        }
        return null;
    }

    public void setTypeMoC(EntityClassification type) {
        this.typeOfCreature = type;
    }

    public int[] getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(int[] dimensions) {
        this.dimensions = dimensions;
    }

    public List<Type> getBiomeTypes() {
        return this.biomeTypes;
    }

    public void setBiomeTypes(List<BiomeDictionary.Type> biomeTypes) {
        this.biomeTypes = biomeTypes;
    }

    public List<Type> getBlockedBiomeTypes() {
        return this.blockedBiomeTypes;
    }

    public void setBlockedBiomeTypes(List<BiomeDictionary.Type> blockedBiomeTypes) {
        this.blockedBiomeTypes = blockedBiomeTypes;
    }

    public int getEntityID() {
        return this.entityId;
    }

    public void setEntityID(int id) {
        this.entityId = id;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void setFrequency(int freq) {
        this.frequency = Math.max(freq, 0);
    }

    public int getMinSpawn() {
        return this.minGroup;
    }

    public void setMinSpawn(int min) {
        this.minGroup = Math.max(min, 0);
    }

    public int getMaxSpawn() {
        return this.maxGroup;
    }

    public void setMaxSpawn(int max) {
        this.maxGroup = Math.max(max, 0);
    }

    public int getMaxInChunk() {
        return this.maxSpawnInChunk;
    }

    public void setMaxInChunk(int max) {
        this.maxSpawnInChunk = Math.max(max, 0);
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String name) {
        this.entityName = name;
    }

    public boolean getCanSpawn() {
        return this.canSpawn;
    }

    public void setCanSpawn(boolean flag) {
        this.canSpawn = flag;
    }

    public MobSpawnInfo.Spawners getSpawnListEntry() {
        return this.spawnListEntry;
    }
}
