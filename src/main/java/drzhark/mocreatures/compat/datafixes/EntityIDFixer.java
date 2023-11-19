/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat.datafixes;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

public class EntityIDFixer implements IFixableData {
    public EntityIDFixer() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getFixVersion() {
        return 1;
    }

    @Override
    public CompoundNBT fixTagCompound(CompoundNBT compound) {
        String entityId = compound.getString("id");
        if (entityId.equals(MoCConstants.MOD_PREFIX + "scorpion")) {
            int entityType = compound.getInteger("TypeInt");
            switch (entityType) {
                case 2:
                    compound.setString("id", MoCConstants.MOD_PREFIX + "cavescorpion");
                    break;
                case 3:
                    compound.setString("id", MoCConstants.MOD_PREFIX + "firescorpion");
                    break;
                case 4:
                    compound.setString("id", MoCConstants.MOD_PREFIX + "frostscorpion");
                    break;
                default:
                    compound.setString("id", MoCConstants.MOD_PREFIX + "dirtscorpion");
            }
            compound.setInteger("TypeInt", 1);
        }
        if (entityId.equals(MoCConstants.MOD_PREFIX + "manticore")) {
            int entityType = compound.getInteger("TypeInt");
            switch (entityType) {
                case 2:
                    compound.setString("id", MoCConstants.MOD_PREFIX + "darkmanticore");
                    break;
                case 3:
                    compound.setString("id", MoCConstants.MOD_PREFIX + "frostmanticore");
                    break;
                case 4:
                    compound.setString("id", MoCConstants.MOD_PREFIX + "toxicmanticore");
                    break;
                default:
                    compound.setString("id", MoCConstants.MOD_PREFIX + "firemanticore");
            }
            compound.setInteger("TypeInt", 1);
        }
        return compound;
    }

    @SubscribeEvent
    public void missingEntityMapping(MissingMappings<EntityEntry> event) {
        ResourceLocation scorpion = new ResourceLocation(MoCConstants.MOD_ID, "scorpion");
        ResourceLocation manticore = new ResourceLocation(MoCConstants.MOD_ID, "manticore");
        for (MissingMappings.Mapping<EntityEntry> entry : event.getAllMappings()) {
            if (entry.key.equals(scorpion) || entry.key.equals(manticore)) {
                entry.ignore();
            }
        }
    }
}
