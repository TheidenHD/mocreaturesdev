/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.tameable;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.UUID;

public class MoCPetData {

    @SuppressWarnings("unused")
    private final UUID ownerUniqueId;
    private final BitSet idMap = new BitSet(Long.SIZE << 4);
    private final ArrayList<Integer> usedPetIds = new ArrayList<>();
    private CompoundNBT ownerData = new CompoundNBT();
    private ListNBT tamedList = new ListNBT();

    public MoCPetData(IMoCTameable pet) {
        this.ownerData.setTag("TamedList", this.tamedList);
        this.ownerUniqueId = MoCreatures.isServer() ? pet.getOwnerId() : Minecraft.getMinecraft().player.getUniqueID();
    }

    public MoCPetData(CompoundNBT nbt, UUID owner) {
        this.ownerData = nbt;
        this.tamedList = nbt.getTagList("TamedList", 10);
        this.ownerUniqueId = owner;
        this.loadPetDataMap(nbt.getCompoundTag("PetIdData"));
    }

    public int addPet(IMoCTameable pet) {
        BlockPos coords = new BlockPos(((Entity) pet).chunkCoordX, ((Entity) pet).chunkCoordY, ((Entity) pet).chunkCoordZ);
        CompoundNBT petNBT = MoCTools.getEntityData((Entity) pet);
        if (this.tamedList != null) {
            int id = getNextFreePetId();
            petNBT.setInteger("PetId", id);
            CompoundNBT petData = petNBT.copy();
            petData.setInteger("ChunkX", coords.getX());
            petData.setInteger("ChunkY", coords.getY());
            petData.setInteger("ChunkZ", coords.getZ());
            petData.setInteger("Dimension", ((Entity) pet).world.provider.getDimensionType().getId());
            this.tamedList.appendTag(petData);
            this.ownerData.setTag("PetIdData", savePetDataMap());
            return id;
        } else {
            return -1;
        }
    }

    public boolean removePet(int id) {
        for (int i = this.tamedList.tagCount() - 1; i >= 0; i--) {
            CompoundNBT nbt = this.tamedList.getCompoundTagAt(i);
            if (nbt.hasKey("PetId") && nbt.getInteger("PetId") == id) {
                this.tamedList.removeTag(i);
                this.usedPetIds.remove(id);
                this.idMap.clear(id); // clear bit so it can be reused again
                if (this.usedPetIds.isEmpty()) {
                    this.idMap.clear(); // fixes bug with ID 0 not able to be used again
                }
                this.ownerData.setTag("PetIdData", savePetDataMap());
                return true;
            }
        }
        return false;
    }

    public CompoundNBT getPetData(int id) {
        if (this.tamedList != null) {
            for (int i = 0; i < this.tamedList.tagCount(); i++) {
                CompoundNBT nbt = this.tamedList.getCompoundTagAt(i);
                if (nbt.hasKey("PetId") && nbt.getInteger("PetId") == id) {
                    return nbt;
                }
            }
        }
        return null;
    }

    public CompoundNBT getOwnerRootNBT() {
        return this.ownerData;
    }

    public ListNBT getTamedList() {
        return this.tamedList;
    }

    public String getOwner() {
        if (this.ownerData != null) {
            return this.ownerData.getString("Owner");
        } else {
            return null;
        }
    }

    public boolean getInAmulet(int petId) {
        CompoundNBT petData = getPetData(petId);
        if (petData != null) {
            return petData.getBoolean("InAmulet");
        }
        return false;
    }

    public void setInAmulet(int petId, boolean flag) {
        CompoundNBT petData = getPetData(petId);
        if (petData != null) {
            petData.setBoolean("InAmulet", flag);
        }
    }

    /**
     * Return the next free pet ID.
     *
     * @return the next free pet ID
     */
    public int getNextFreePetId() {
        int next = 0;
        while (true) {
            next = this.idMap.nextClearBit(next);
            if (this.usedPetIds.contains(next)) {
                this.idMap.set(next);
            } else {
                this.usedPetIds.add(next);
                return next;
            }
        }
    }

    public CompoundNBT savePetDataMap() {
        int[] data = new int[(this.idMap.length() + Integer.SIZE - 1) / Integer.SIZE];
        CompoundNBT dataMap = new CompoundNBT();
        for (int i = 0; i < data.length; i++) {
            int val = 0;
            for (int j = 0; j < Integer.SIZE; j++) {
                val |= this.idMap.get(i * Integer.SIZE + j) ? (1 << j) : 0;
            }
            data[i] = val;
        }
        dataMap.setIntArray("PetIdArray", data);
        return dataMap;
    }

    public void loadPetDataMap(CompoundNBT compoundTag) {
        if (compoundTag == null) {
            this.idMap.clear();
        } else {
            int[] intArray = compoundTag.getIntArray("PetIdArray");
            for (int i = 0; i < intArray.length; i++) {
                for (int j = 0; j < Integer.SIZE; j++) {
                    this.idMap.set(i * Integer.SIZE + j, (intArray[i] & (1 << j)) != 0);
                }
            }
            // populate our usedPetIds
            int next = 0;
            while (true) {
                next = this.idMap.nextClearBit(next);
                if (!this.usedPetIds.contains(next)) {
                    this.usedPetIds.add(next);
                } else {
                    break;
                }
            }
        }
    }
}
