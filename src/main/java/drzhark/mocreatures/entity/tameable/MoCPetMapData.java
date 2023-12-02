/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.tameable;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.DimensionManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.UUID;

public class MoCPetMapData extends WorldSavedData {

    private final Object2ObjectOpenHashMap<UUID, MoCPetData> petMap = new Object2ObjectOpenHashMap<>();

    public MoCPetMapData(String par1Str) {
        super(par1Str);
        this.markDirty();
    }

    /**
     * Get a list of pets.
     */
    public MoCPetData getPetData(UUID ownerUniqueId) {
        return this.petMap.get(ownerUniqueId);
    }

    public Object2ObjectOpenHashMap<UUID, MoCPetData> getPetMap() {
        return this.petMap;
    }

    public void removeOwnerPet(IMoCTameable pet, int petId) {
        UUID owner = pet.getOwnerId();
        if (this.petMap.get(owner) != null && this.petMap.get(owner).removePet(petId)) {
            this.markDirty();
            pet.setOwnerPetId(-1);
        }
    }

    public void updateOwnerPet(IMoCTameable pet) {
        this.markDirty();
        if (pet.getOwnerPetId() == -1 || this.petMap.get(pet.getOwnerId()) == null) {
            UUID owner = MoCreatures.isServer() ? pet.getOwnerId() : Minecraft.getMinecraft().player.getUniqueID();
            MoCPetData petData;
            int id;
            if (this.petMap.containsKey(owner)) {
                petData = this.petMap.get(owner);
                id = petData.addPet(pet);
            } else // create new pet data
            {
                petData = new MoCPetData(pet);
                id = petData.addPet(pet);
                this.petMap.put(owner, petData);
            }
            pet.setOwnerPetId(id);
        } else {
            // update pet data
            UUID owner = pet.getOwnerId();
            MoCPetData petData = this.getPetData(owner);
            CompoundNBT rootNBT = petData.getOwnerRootNBT();
            ListNBT tag = rootNBT.getList("TamedList", 10);
            int id;
            id = pet.getOwnerPetId();

            for (int i = 0; i < tag.size(); i++) {
                CompoundNBT nbt = tag.getCompound(i);
                if (nbt.getInt("PetId") == id) {
                    // Update what we need for commands
                    nbt.put("Pos", this.newDoubleNBTList(((Entity) pet).getPosX(), ((Entity) pet).getPosY(), ((Entity) pet).getPosZ()));
                    nbt.putInt("ChunkX", ((Entity) pet).chunkCoordX);
                    nbt.putInt("ChunkY", ((Entity) pet).chunkCoordY);
                    nbt.putInt("ChunkZ", ((Entity) pet).chunkCoordZ);
                    nbt.putInt("Dimension", ((Entity) pet).world.provider.getDimensionType().getId());
                    nbt.putInt("PetId", pet.getOwnerPetId());
                }
            }
        }
    }

    protected ListNBT newDoubleNBTList(double... par1ArrayOfDouble) {
        ListNBT nbttaglist = new ListNBT();
        for (double d1 : par1ArrayOfDouble) {
            nbttaglist.add(new NBTTagDouble(d1));
        }
        return nbttaglist;
    }

    public boolean isExistingPet(UUID owner, IMoCTameable pet) {
        MoCPetData petData = MoCreatures.instance.mapData.getPetData(owner);
        if (petData != null) {
            ListNBT tag = petData.getTamedList();
            for (int i = 0; i < tag.size(); i++) {
                CompoundNBT nbt = tag.getCompound(i);
                if (nbt.getInt("PetId") == pet.getOwnerPetId()) {
                    // found existing pet
                    return true;
                }
            }
        }
        return false;
    }

    public void forceSave() {
        if (DimensionManager.getWorld(0) != null) {
            ISaveHandler saveHandler = DimensionManager.getWorld(0).getSaveHandler();
            try {
                File file1 = saveHandler.getMapFileFromName(MoCConstants.MOD_ID);
                CompoundNBT nbttagcompound = new CompoundNBT();
                this.writeToNBT(nbttagcompound);
                CompoundNBT nbttagcompound1 = new CompoundNBT();
                nbttagcompound1.put("data", nbttagcompound);
                FileOutputStream fileoutputstream = new FileOutputStream(file1);
                CompressedStreamTools.writeCompressed(nbttagcompound1, fileoutputstream);
                fileoutputstream.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * reads in data from the CompoundNBT into this MapDataBase
     */
    @Override
    public void readFromNBT(CompoundNBT par1NBTTagCompound) {
        for (String s : par1NBTTagCompound.getKeySet()) {
            CompoundNBT nbt = (CompoundNBT) par1NBTTagCompound.getTag(s);
            UUID ownerUniqueId = UUID.fromString(s);
            if (!this.petMap.containsKey(ownerUniqueId)) {
                this.petMap.put(ownerUniqueId, new MoCPetData(nbt, ownerUniqueId));
            }
        }
    }

    /**
     * write data to CompoundNBT from this MapDataBase, similar to Entities
     * and TileEntities
     */
    @Override
    public CompoundNBT writeToNBT(CompoundNBT par1NBTTagCompound) {
        for (Map.Entry<UUID, MoCPetData> ownerEntry : this.petMap.entrySet()) {
            try {
                if (ownerEntry.getKey() != null) {
                    par1NBTTagCompound.put(ownerEntry.getKey().toString(), ownerEntry.getValue().getOwnerRootNBT());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return par1NBTTagCompound;
    }
}
